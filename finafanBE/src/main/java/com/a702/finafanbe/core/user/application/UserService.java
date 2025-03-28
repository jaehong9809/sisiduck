package com.a702.finafanbe.core.user.application;

import com.a702.finafanbe.core.user.dto.request.UserFinancialNetworkRequest;
import com.a702.finafanbe.core.user.dto.response.UserFinancialNetworkResponse;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialNetworkUtil;
import com.a702.finafanbe.global.common.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final FinancialNetworkUtil financialNetworkUtil;

//    public User signUp(UserRequest userRequest) {
//        getUser(userRequest);
//        return userRepository.save(User.of(userRequest));
//    }
//
//    private void getUser(UserRequest userRequest) {
//        userRepository.findByPhoneNumber(userRequest.phoneNumber()).orElseThrow(
//                ParameterException::new);
//    }

    public void signUpWithFinancialNetwork(
        String userEmail
    ) {

        User user = findUser(userEmail);
        ResponseEntity<UserFinancialNetworkResponse> exchange = requestFinancialNetwork(
                "https://finopenapi.ssafy.io/ssafy/api/v1/member",
                user.getSocialEmail()
        );
        userRepository.save(
                User.of(
                        userEmail,
                        exchange.getBody().userKey(),
                        "SSAFY"
                )
        );
    }


    public UserFinancialNetworkResponse getUserWithFinancialNetwork(String userEmail) {
        User user = findUser(userEmail);
        ResponseEntity<UserFinancialNetworkResponse> exchange = requestFinancialNetwork(
                "https://finopenapi.ssafy.io/ssafy/api/v1/member/search",
                user.getSocialEmail()
        );
        return exchange.getBody();
    }

    public User findUser(String userEmail) {
        return userRepository.findBySocialEmail(userEmail).orElseThrow(() -> new BadRequestException(ResponseData.builder()
                .code(ErrorCode.NotFoundUser.getCode())
                .message(ErrorCode.NotFoundUser.getMessage())
                .build()));
    }

    private ResponseEntity<UserFinancialNetworkResponse> requestFinancialNetwork(
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
        );
    }
}
