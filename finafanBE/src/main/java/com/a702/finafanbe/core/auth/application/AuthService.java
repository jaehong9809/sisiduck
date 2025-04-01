package com.a702.finafanbe.core.auth.application;

import com.a702.finafanbe.core.auth.dto.request.TokenRequest;
import com.a702.finafanbe.core.auth.entity.infrastructure.SSAFYUserInfo;
import com.a702.finafanbe.core.auth.entity.infrastructure.SsafyOAuthProvider;
import com.a702.finafanbe.core.auth.entity.AuthTokens;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.core.auth.presentation.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final SsafyOAuthProvider ssafyOAuthProvider;
    private final UserRepository userRepository;

    public AuthTokens login(TokenRequest tokenRequest) {
        String ssafyAccessToken = ssafyOAuthProvider.fetchSSAFYAccessToken(tokenRequest.code());
        SSAFYUserInfo userInfo = ssafyOAuthProvider.getUserInfo(ssafyAccessToken);

        User user = findOrCreateUser(
                userInfo.getSocialEmail(),
                userInfo.getNickname()
        );

        AuthTokens authTokens = jwtUtil.createLoginToken(user.getUserId().toString());
        //TODO refreshToken
        return authTokens;
    }

    private User findOrCreateUser(
            String socialEmail,
            String nickname
    ) {
        return userRepository.findBySocialEmail(
                socialEmail
        ).orElseGet(() -> createUser(
                socialEmail,
                nickname
        ));
    }

    private User createUser(String socialEmail, String nickname) {
        String generatedNickName = nickname + "#" + socialEmail;
        return userRepository.save(User.of(
                socialEmail,
                generatedNickName
        ));
    }
}
