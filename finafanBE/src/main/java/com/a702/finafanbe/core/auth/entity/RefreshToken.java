package com.a702.finafanbe.core.auth.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "jwt", timeToLive = 60*60*24*7) //1week
public class RefreshToken {

    private Long userId;
    @Id
    private String value;
}
