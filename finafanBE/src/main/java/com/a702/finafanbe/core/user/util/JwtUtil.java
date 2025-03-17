package com.a702.finafanbe.core.user.util;

import com.a702.finafanbe.core.user.entity.AuthTokens;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private SecretKey secretKey;
    private Long accessTokenExpiry;
    private Long refreshTokenExpiry;

    public JwtUtil(
            @Value("jwt.secretkey") SecretKey secretKey,
            @Value("jwt.access-expiration") Long accessTokenExpiration,
            @Value("jwt.refresh-expiration") Long refreshTokenExpiry
    ) {
        this.secretKey = secretKey;
        this.accessTokenExpiry = accessTokenExpiration;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    public AuthTokens createLoginToken(String subject) {
        String refreshToken = createToken("", refreshTokenExpiry);
        String accessToken = createToken(subject, accessTokenExpiry);
        return new AuthTokens(refreshToken, accessToken);
    }

    private String createToken(String subject, Long expiredMs) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
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
