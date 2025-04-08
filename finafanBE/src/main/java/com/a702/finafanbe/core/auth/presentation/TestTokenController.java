package com.a702.finafanbe.core.auth.presentation;

import com.a702.finafanbe.core.auth.entity.AuthTokens;
import com.a702.finafanbe.core.auth.presentation.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/access-token")
@RequiredArgsConstructor
public class TestTokenController {

    private static final String DEFAULT_ID = "2";

    private final JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<AuthTokens> retrieveTestToken() {
        return ResponseEntity.ok(jwtUtil.createLoginToken(DEFAULT_ID));
    }
}
