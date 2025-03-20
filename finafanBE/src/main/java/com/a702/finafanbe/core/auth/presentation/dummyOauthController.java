//package com.a702.finafanbe.core.auth.presentation;
//
//import com.a702.finafanbe.core.auth.dto.response.TokenResponse;
//import com.a702.finafanbe.core.auth.entity.infrastructure.SsafyOAuthProvider;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//public class dummyOauthController {
//
//    @Value("${oauth.client-id}")
//    private String clientId;
//
//    @Value("${oauth.redirect-uri}")
//    private String redirectUri;
//
//    @Value("${oauth.auth-url}")
//    private String authUrl;
//
//    @GetMapping("/oauth/authorize")
//    public ResponseEntity<String> getAuthorizationCode() {
//        String url = String.format("%s?client_id=%s&redirect_uri=%s&response_type=code",
//                authUrl, clientId, redirectUri);
//
//        // URL 생성 확인용 (확인 후 삭제 가능)
//        System.out.println("Authorization URL: " + url);
//
//        return ResponseEntity.ok("Redirect to: " + url);
//    }
//
//}
//
