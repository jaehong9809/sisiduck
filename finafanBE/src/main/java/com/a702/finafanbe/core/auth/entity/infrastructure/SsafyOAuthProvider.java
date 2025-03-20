package com.a702.finafanbe.core.auth.entity.infrastructure;

import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class SsafyOAuthProvider {

    private final RestTemplate restTemplate;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String tokenUri;
    private final String userInfoUri;

    public SsafyOAuthProvider(
            RestTemplate restTemplate,
            @Value("${spring.security.oauth2.client.registration.ssafy.client-id}") String clientId,
            @Value("${spring.security.oauth2.client.registration.ssafy.client-secret}") String clientSecret,
            @Value("${spring.security.oauth2.client.registration.ssafy.redirect-uri}") String redirectUri,
            @Value("${spring.security.oauth2.client.provider.ssafy.token-uri}") String tokenUri,
            @Value("${spring.security.oauth2.client.provider.ssafy.user-info-uri}") String userInfoUri
    ){
        this.restTemplate = restTemplate;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.tokenUri = tokenUri;
        this.userInfoUri = userInfoUri;
    }


    public String fetchSSAFyAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id",clientId);
        params.add("client_secret",clientSecret);
        params.add("redirect_uri",redirectUri);
        params.add("code",code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(
                params,
                headers
        );

        log.info("token uri: {}", tokenUri);
        log.info("redirect url : {} ", redirectUri);

        //TODO : httpClient[주호]
        ResponseEntity<TokenResponse> response = restTemplate.exchange(
                tokenUri,
                HttpMethod.POST,
                requestEntity,
                TokenResponse.class
        );

        return Optional.ofNullable(response.getBody())
                .orElseThrow(
                        ()->new BadRequestException(ResponseData.<Void>builder()
                                .code(ErrorCode.TOKEN_ERROR.getCode())
                                .message(ErrorCode.TOKEN_ERROR.getMessage())
                                .build())
                ).getAccessToken();
    }

    public SSAFYUserInfo getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, Boolean> params = new HashMap<>();
        params.put("secure_resource",true);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<SSAFYUserInfo> response = restTemplate.exchange(
                userInfoUri,
                HttpMethod.GET,
                requestEntity,
                SSAFYUserInfo.class,
                params
        );
        if(response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new BadRequestException(ResponseData.<Void>builder()
                .code(ErrorCode.TOKEN_ERROR.getCode())
                .message(ErrorCode.TOKEN_ERROR.getMessage())
                .build());
    }

    @Getter
    public static class TokenResponse {
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("scope")
        private String scope;
        @JsonProperty("expires_in")
        private String expiresIn;
        @JsonProperty("refresh_token")
        private String refreshToken;
        @JsonProperty("refresh_token_expires_in")
        private Integer refreshTokenExpiresIn;
    }
}
