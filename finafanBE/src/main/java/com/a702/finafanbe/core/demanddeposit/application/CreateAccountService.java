package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.CreateAccountRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.CreateAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CreateAccountService {

    private final RestTemplate restTemplate;


    public ResponseEntity<CreateAccountResponse> createAccount(CreateAccountRequest createAccountRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateAccountRequest> httpEntity = new HttpEntity<>(
                createAccountRequest,
                headers
        );
        ResponseEntity<CreateAccountResponse> exchange = restTemplate.exchange(
                "https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/createDemandDepositAccount",
                HttpMethod.POST,
                httpEntity,
                CreateAccountResponse.class
        );

        return ResponseEntity.ok(exchange.getBody());

    }
}
