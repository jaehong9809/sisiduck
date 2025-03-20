package com.a702.finafanbe.core.auth.presentation;

import com.a702.finafanbe.core.auth.application.AuthService;
import com.a702.finafanbe.core.auth.dto.request.TokenRequest;
import com.a702.finafanbe.core.auth.dto.response.TokenResponse;
import com.a702.finafanbe.core.auth.entity.AuthTokens;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login/ssafy")
    public ResponseEntity<TokenResponse> login(
            @RequestBody TokenRequest tokenRequest
    ) {
        AuthTokens authTokens = authService.login(tokenRequest);
        return ResponseEntity.ok(new TokenResponse(authTokens.accessToken()));
    }
}
