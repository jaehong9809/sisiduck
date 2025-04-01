package com.a702.finafanbe.core.ble.application;

import com.a702.finafanbe.core.ble.dto.request.RegisterBleUuidRequest;
import com.a702.finafanbe.global.common.util.RedisKeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class BleService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final Long MINUTES = 7L;
    private static final Duration TTL = Duration.ofMinutes(MINUTES);

    public void registerUuid(RegisterBleUuidRequest request, Long userId) {
        String key = RedisKeyUtil.bleUuidKey(request.uuid());
        redisTemplate.opsForValue().set(key, userId.toString(), TTL);
    }
}
