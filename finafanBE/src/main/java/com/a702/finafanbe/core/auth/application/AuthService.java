package com.a702.finafanbe.core.auth.application;

import com.a702.finafanbe.core.auth.entity.infrastructure.SSAFYUserInfo;
import com.a702.finafanbe.core.auth.entity.infrastructure.SsafyOAuthProvider;
import com.a702.finafanbe.core.auth.entity.AuthTokens;
import com.a702.finafanbe.core.demanddeposit.application.ExternalDemandDepositApiService;
import com.a702.finafanbe.core.demanddeposit.dto.request.DepositRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.CreateAccountResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.CreateAccountResponse.REC;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountDepositResponse;
import com.a702.finafanbe.core.user.dto.request.UserFinancialNetworkRequest;
import com.a702.finafanbe.core.user.dto.response.UserFinancialNetworkResponse;
import com.a702.finafanbe.core.user.dto.response.UserResponse;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.core.auth.presentation.util.JwtUtil;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialNetworkUtil;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialRequestFactory;
import com.a702.finafanbe.global.common.response.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final SsafyOAuthProvider ssafyOAuthProvider;
    private final UserRepository userRepository;
    private final FinancialNetworkUtil financialNetworkUtil;
    private final RestTemplate restTemplate;
    private final FinancialRequestFactory financialRequestFactory;
    private final ExternalDemandDepositApiService externalDemandDepositApiService;

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

    private User createUser(String userEmail, String name) {
        String generatedNickName = name + "#" + userEmail;
        String userKey = "";
        try {
            ResponseEntity<UserFinancialNetworkResponse> financialNetwork = requestFinancialNetwork(
                    "https://finopenapi.ssafy.io/ssafy/api/v1/member",
                    userEmail
            );
            userKey= financialNetwork.getBody().userKey();
        }catch (Exception e) {
            log.error("Failed to request financial network for user: {}, error: {}", userEmail, e.getMessage());
        }

        REC dummyAccount = createDummyAccount(userEmail, "001-1-56b59a5f38c04f");
        depositToDummyAccount(userEmail,
            new DepositRequest(dummyAccount.getAccountNo(), 100000000L, ""));

        return saveUser(userEmail, userKey, name);
    }

    private ResponseEntity<UpdateDemandDepositAccountDepositResponse> depositToDummyAccount(
        String userEmail,
        DepositRequest depositRequest
    ) {
        return externalDemandDepositApiService.DemandDepositRequestWithFactory(
            "/demandDeposit/updateDemandDepositAccountDeposit",
            apiName -> financialRequestFactory.depositAccount(
                userEmail,
                apiName,
                depositRequest.accountNo(),
                depositRequest.transactionBalance(),
                depositRequest.transactionSummary()
            ),
            "updateDemandDepositAccountDeposit",
            UpdateDemandDepositAccountDepositResponse.class
        );
    }

    private REC createDummyAccount(String email, String productUniqueNo) {
        return externalDemandDepositApiService.DemandDepositRequestWithFactory(
            "/demandDeposit/createDemandDepositAccount",
            apiName -> financialRequestFactory.UserKeyAccountTypeUniqueNoRequest(
                email,
                productUniqueNo,
                apiName
            ),
            "createDemandDepositAccount",
            CreateAccountResponse.class
        ).getBody().REC();
    }

    @Transactional
    protected User saveUser(String userEmail, String userKey, String name) {
        if(userRepository.existsBySocialEmail(userEmail)){
            throw new BadRequestException(ResponseData.createResponse(ErrorCode.DUPLICATE_EMAIL));
        }
        return userRepository.save(
            User.of(
                userEmail,
                userKey,
                "SSAFY",
                name
            )
        );
    }

    public ResponseEntity<UserFinancialNetworkResponse> requestFinancialNetwork(
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
        log.info(url + " " + userEmail);
        return restTemplate.exchange(
            url,
            HttpMethod.POST,
            httpEntity,
            UserFinancialNetworkResponse.class
        );
    }

    public UserResponse getUserWithFinancialNetwork(String userEmail) {
        User user = findUserByEmail(userEmail);
        UserFinancialNetworkResponse userFinancialNetworkResponse = requestFinancialNetwork(
            "https://finopenapi.ssafy.io/ssafy/api/v1/member/search",
            user.getSocialEmail()
        ).getBody();
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
}
