package com.a702.finafanbe.core.user.application;

import com.a702.finafanbe.core.user.dto.request.UserFinancialNetworkRequest;
import com.a702.finafanbe.core.user.dto.response.UserFinancialNetworkResponse;
import com.a702.finafanbe.core.user.dto.response.UserResponse;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.exception.NotFoundException;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialNetworkUtil;
import com.a702.finafanbe.global.common.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final FinancialNetworkUtil financialNetworkUtil;

    public User getUser(Long userId) {
        return findUserOrThrow(userId);
    }

    public UserResponse signUpWithFinancialNetwork(
        String userEmail
    ) {

        if(userRepository.existsBySocialEmail(userEmail)){
            throw new BadRequestException(ResponseData.createResponse(ErrorCode.DUPLICATE_EMAIL));
        }
        User ssafy = userRepository.save(
            User.of(
                userEmail,
                requestFinancialNetwork(
                    "https://finopenapi.ssafy.io/ssafy/api/v1/member",
                    userEmail
                ).userKey(),
                "SSAFY"
            )
        );
        return new UserResponse(
            ssafy.getSocialEmail(),
            ssafy.getName()
        );
    }

    public UserResponse getUserWithFinancialNetwork(String userEmail) {
        User user = findUserByEmail(userEmail);
        UserFinancialNetworkResponse userFinancialNetworkResponse = requestFinancialNetwork(
            "https://finopenapi.ssafy.io/ssafy/api/v1/member/search",
            user.getSocialEmail()
        );
        return new UserResponse(
            userFinancialNetworkResponse.userId(),
            userFinancialNetworkResponse.userName()
        );
    }

    public User findUserByEmail(String userEmail) {
        return userRepository.findBySocialEmail(userEmail).orElseThrow(() -> new BadRequestException(ResponseData.builder()
                .code(ErrorCode.NotFoundUser.getCode())
                .message(ErrorCode.NotFoundUser.getMessage())
                .build()));
    }

    private UserFinancialNetworkResponse requestFinancialNetwork(
            String url,
            String userEmail
    ) {
        UserFinancialNetworkRequest userFinancialNetworkRequest = new UserFinancialNetworkRequest(
                financialNetworkUtil.getApiKey(),
                userEmail
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserFinancialNetworkRequest> httpEntity = new HttpEntity<>(
                userFinancialNetworkRequest,
                headers
        );
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                UserFinancialNetworkResponse.class
        ).getBody();
    }

    @Cacheable(value = "starId", key = "'user:' + #userId + ':starId'")
    public Long getUserStarId(Long userId) {
        User user = findUserOrThrow(userId);
        return user.getEntertainerId();
    }

    @Transactional
    @CachePut(value = "starId", key = "'user:' + #userId + ':starId'")
    public Long updateUserStarId(Long userId, Long newStarId) {
        User user = findUserOrThrow(userId);
        user.updateFavoriteEntertainer(newStarId);
        userRepository.save(user);
        return newStarId;
    }

    private User findUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        ResponseData.builder()
                                .code(ErrorCode.NotFoundUser.getCode())
                                .message(ErrorCode.NotFoundUser.getMessage())
                                .build()));
    }
}
