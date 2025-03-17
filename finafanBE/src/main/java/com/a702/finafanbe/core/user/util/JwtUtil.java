package com.a702.finafanbe.core.user.util;

import com.a702.finafanbe.core.user.entity.AuthTokens;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private SecretKey secretKey;
    private Long accessTokenExpiration;
    private Long refreshTokenExpiration;

    public JwtUtil(
            @Value("jwt.secretkey") SecretKey secretKey,
            @Value("jwt.access-expiration") Long accessTokenExpiration,
            @Value("jwt.refresh-expiration") Long refreshTokenExpiration
    ) {
        this.secretKey = secretKey;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }



}
