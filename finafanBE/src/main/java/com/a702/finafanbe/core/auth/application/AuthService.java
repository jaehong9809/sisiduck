package com.a702.finafanbe.core.auth.application;

import com.a702.finafanbe.core.auth.entity.infrastructure.SSAFYUserInfo;
import com.a702.finafanbe.core.auth.entity.infrastructure.SsafyOAuthProvider;
import com.a702.finafanbe.core.auth.entity.AuthTokens;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.core.auth.presentation.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final SsafyOAuthProvider ssafyOAuthProvider;
    private final UserRepository userRepository;

    public AuthTokens login(String code) {
        String ssafyAccessToken = ssafyOAuthProvider.fetchSSAFYAccessToken(code);
        log.info("✨ SSAFY access token: {}", ssafyAccessToken);

        SSAFYUserInfo userInfo = ssafyOAuthProvider.getUserInfo(ssafyAccessToken);
        log.info("✨ SSAFY User Email: {}", userInfo.getEmail());
        log.info("✨ SSAFY User Name: {}", userInfo.getName());

        User user = findOrCreateUser(
                userInfo.getEmail(),
                userInfo.getName()
        );

        log.info("✨ UserId for JWT subject: {}", user.getUserId());
        AuthTokens authTokens = jwtUtil.createLoginToken(user.getUserId().toString());
        //TODO refreshToken
        return authTokens;
    }

    private User findOrCreateUser(
            String socialEmail,
            String name
    ) {
        return userRepository.findBySocialEmail(
                socialEmail
        ).orElseGet(() -> createUser(
                socialEmail,
                name
        ));
    }

    private User createUser(String socialEmail, String name) {
        String generatedNickName = name + "#" + socialEmail;
        return userRepository.save(User.of(
                socialEmail,
                name
        ));
    }
}
