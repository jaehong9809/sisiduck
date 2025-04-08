package com.a702.finafanbe.core.auth.presentation.util;

import com.a702.finafanbe.core.auth.entity.AuthTokens;
import com.a702.finafanbe.global.common.exception.InvalidJwtException;
import com.a702.finafanbe.global.common.response.ResponseData;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.a702.finafanbe.global.common.exception.ErrorCode.INVALID_TOKEN_REQUEST;

@Slf4j
@Component
public class JwtUtil {

    private SecretKey secretKey;
    private Long accessTokenExpiry;
    private Long refreshTokenExpiry;

    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token-validity-in-seconds}") Long accessTokenExpiration,
            @Value("${jwt.refresh-token-validity-in-seconds}") Long refreshTokenExpiry
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiry = accessTokenExpiration;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    public AuthTokens createLoginToken(String subject) {
        log.info("✅ [JwtUtil] subject value check: '{}'", subject);
        String refreshToken = createToken("", refreshTokenExpiry);
        String accessToken = createToken(subject, accessTokenExpiry);
        return new AuthTokens(accessToken, refreshToken);
    }

    private String createToken(String subject, Long expiredMs) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    public void validateRefreshToken(String refreshToken) {
        try {
            parseToken(refreshToken);
        } catch (JwtException e) {
            throw new InvalidJwtException(ResponseData.<Void>builder()
                    .code(INVALID_TOKEN_REQUEST.getCode())
                    .message(INVALID_TOKEN_REQUEST.getMessage())
                    .build());
        }
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.secretKey)
                .build()
                .parseClaimsJws(token);
    }

    public String getSubject(String token) {
        return parseToken(token).getBody().getSubject();
    }

    public boolean isTokenExpired(String token) {
        try{
            parseToken(token);
        }catch (Exception e){
            return true;
        }
        return false;
    }

    public boolean isTokenValid(String token) {
        try {
            parseToken(token);
        }catch (JwtException e){
            return false;
        }
        return true;
    }
}
