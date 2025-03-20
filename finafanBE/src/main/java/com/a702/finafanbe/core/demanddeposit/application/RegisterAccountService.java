package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.RegisterDemandDepositRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.RegisterDemandDepositResponse;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RegisterAccountService {

    private final RestTemplate restTemplate;
    private final AccountRepository accountRepository;

    public ResponseEntity<RegisterDemandDepositResponse> registerDemandDeposit(RegisterDemandDepositRequest createAccountRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterDemandDepositRequest> httpEntity = new HttpEntity<>(
                createAccountRequest,
                headers
        );
        ResponseEntity<RegisterDemandDepositResponse> exchange = restTemplate.exchange(
                "https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/createDemandDeposit",
                HttpMethod.POST,
                httpEntity,
                RegisterDemandDepositResponse.class
        );

        return ResponseEntity.ok(exchange.getBody());

    }
}
