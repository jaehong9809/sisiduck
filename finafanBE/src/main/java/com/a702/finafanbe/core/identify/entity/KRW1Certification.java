package com.a702.finafanbe.core.identify.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@RedisHash(value = "KRW1Certification", timeToLive = 600)
public class KRW1Certification {

    private Long userId;
    @Id
    private String value;
}
