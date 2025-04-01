package com.a702.finafanbe.core.entertainer.application;

import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerRepository;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.exception.NotFoundException;
import com.a702.finafanbe.global.common.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntertainerService {

    private final EntertainerRepository entertainerRepository;

    // 캐시 register (Spring Cache Redis)
    @Cacheable(
            value = "entertainerProfile",
            key = "'entertainer:' + #entertainerId"
    )
    public String getEntertainerProfileUrl(Long entertainerId) {
        Entertainer entertainer = getEntertainer(entertainerId);
        return entertainer.getEntertainerProfileUrl();
    }

    // 캐시 update
    @CacheEvict(
            value = "entertainerProfile",
            key = "'entertainer:' + #entertainerId"
    )
    public void updateEntertainerProfile(Long entertainerId, String newProfileUrl) {
        Entertainer entertainer = getEntertainer(entertainerId);
        entertainer.updateProfileUrl(newProfileUrl);
    }

    private Entertainer getEntertainer(Long entertainerId) {
        return entertainerRepository.findById(entertainerId)
                .orElseThrow(() -> new NotFoundException(ResponseData.builder()
                        .code(ErrorCode.NotFoundUser.getCode())
                        .message(ErrorCode.NotFoundUser.getMessage())
                        .build()));
    }
}
