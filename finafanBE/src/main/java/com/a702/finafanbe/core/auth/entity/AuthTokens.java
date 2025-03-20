package com.a702.finafanbe.core.auth.entity;

public record AuthTokens (
        String accessToken,
        String refreshToken
){
    public AuthTokens of(
            String accessToken,
            String refreshToken
    ) {
        return new AuthTokens(
                accessToken,
                refreshToken
        );
    }
}
