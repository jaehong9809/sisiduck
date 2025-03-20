package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.InquireDemandDepositAccountRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountListResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountResponse;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class InquireDemandDepositAccountService {

    private final RestTemplate restTemplate;
    private final AccountRepository accountRepository;

    public ResponseEntity<InquireDemandDepositAccountResponse> retrieveDemandDepositAccount(
            InquireDemandDepositAccountRequest retrieveDemandDepositRequest
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InquireDemandDepositAccountRequest> httpEntity = new HttpEntity<>(
                retrieveDemandDepositRequest,
                headers
        );
        ResponseEntity<InquireDemandDepositAccountResponse> exchange = restTemplate.exchange(
                "https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireDemandDepositAccount",
                HttpMethod.POST,
                httpEntity,
                InquireDemandDepositAccountResponse.class
        );

        return ResponseEntity.ok(exchange.getBody());
    }

    public ResponseEntity<InquireDemandDepositAccountListResponse> retrieveDemandDepositAccountList(
            InquireDemandDepositAccountRequest.RetrieveDemandDepositRequestHeader retrieveDemandDepositRequestHeader
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InquireDemandDepositAccountRequest.RetrieveDemandDepositRequestHeader> httpEntity = new HttpEntity<>(
                retrieveDemandDepositRequestHeader,
                headers
        );
        ResponseEntity<InquireDemandDepositAccountListResponse> exchange = restTemplate.exchange(
                "https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireDemandDepositAccountList",
                HttpMethod.POST,
                httpEntity,
                InquireDemandDepositAccountListResponse.class
        );

        return ResponseEntity.ok(exchange.getBody());
    }
}

