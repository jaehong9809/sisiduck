package com.a702.finafanbe.core.ble.application;

import com.a702.finafanbe.core.ble.dto.request.RegisterBleUuidRequest;
import com.a702.finafanbe.core.ble.dto.response.MatchFansResponse;
import com.a702.finafanbe.core.entertainer.application.EntertainerService;
import com.a702.finafanbe.core.user.application.UserService;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.global.common.util.RedisKeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BleService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final Long MINUTES = 7L;
    private static final Long MAX_FAN = 10L;
    private static final Duration TTL = Duration.ofMinutes(MINUTES);
    private final UserService userService;
    private final EntertainerService entertainerService;

    public void registerUuid(RegisterBleUuidRequest request, Long userId) {
        String key = RedisKeyUtil.bleUuidKey(request.uuid());
        redisTemplate.opsForValue().set(key, userId.toString(), TTL);
    }

    @Transactional(readOnly = true)
    public List<MatchFansResponse> findMatchFans(List<String> uuids, Long myUserId) {
        Long myStarId = userService.getUserStarId(myUserId);

        List<Long> matchedUserIds = uuids.stream()
                .limit(MAX_FAN)
                .map(uuid -> redisTemplate.opsForValue().get(RedisKeyUtil.bleUuidKey(uuid)))
                .filter(Objects::nonNull)
                .map(Long::parseLong)
                .filter(userId -> !userId.equals(myUserId))
                .filter(userId -> {
                    Long targetStarId = userService.getUserStarId(userId);
                    return myStarId.equals(targetStarId);
                })
                .toList();

        return matchedUserIds.stream()
                .map(userId -> {
                    User user = userService.getUser(userId);
                    Long starId = user.getEntertainerId();
                    String imgUrl = entertainerService.getEntertainerProfileUrl(starId);
                    return MatchFansResponse.of(user, imgUrl);
                })
                .toList();
    }
}
