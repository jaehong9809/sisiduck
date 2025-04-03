package com.a702.finafanbe.core.auth.presentation;

import com.a702.finafanbe.core.auth.application.AuthService;
import com.a702.finafanbe.core.auth.dto.request.TokenRequest;
import com.a702.finafanbe.core.auth.dto.response.TokenResponse;
import com.a702.finafanbe.core.auth.entity.AuthTokens;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private static final String DEEPLINK_URL = "com.a702.finafan://login?token=";

    @GetMapping("/login/ssafy")
    public ResponseEntity<Void> ssafyOauthCallback(
            @RequestParam("code") String code
    ) throws IOException {
        AuthTokens authTokens = authService.login(code);

        // Server Callback 받은 후 -> Android Client에 redirectUri 던지는 방식으로 수정
        String redirectUri = DEEPLINK_URL + URLEncoder.encode(authTokens.accessToken(), "UTF-8");
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(redirectUri))
                .build();
    }
}
