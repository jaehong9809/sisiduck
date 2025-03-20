package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.RetrieveDemandDepositListRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.RetrieveDemandDepositListResponse;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RetrieveAccountService {

    private final RestTemplate restTemplate;
    private final AccountRepository accountRepository;

    public ResponseEntity<RetrieveDemandDepositListResponse> retrieveDemandDepositList(RetrieveDemandDepositListRequest retrieveDemandDepositListRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RetrieveDemandDepositListRequest> httpEntity = new HttpEntity<>(
                retrieveDemandDepositListRequest,
                headers
        );
        ResponseEntity<RetrieveDemandDepositListResponse> exchange = restTemplate.exchange(
                "https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireDemandDepositList",
                HttpMethod.POST,
                httpEntity,
                RetrieveDemandDepositListResponse.class
        );

        return ResponseEntity.ok(exchange.getBody());
    }

}
