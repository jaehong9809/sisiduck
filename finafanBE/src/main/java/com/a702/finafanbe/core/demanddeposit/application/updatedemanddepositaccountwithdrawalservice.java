package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.UpdateDemandDepositAccountWithdrawalRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountWithdrawalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class updatedemanddepositaccountwithdrawalservice {

    private final RestTemplate restTemplate;

    public ResponseEntity<?> withdrawal(
            UpdateDemandDepositAccountWithdrawalRequest updateDemandDepositAccountWithdrawalRequest
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UpdateDemandDepositAccountWithdrawalRequest> httpEntity = new HttpEntity<>(
                updateDemandDepositAccountWithdrawalRequest,
                headers
        );
        ResponseEntity<UpdateDemandDepositAccountWithdrawalResponse> exchange = restTemplate.exchange(
                "https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/updateDemandDepositAccountWithdrawal",
                HttpMethod.POST,
                httpEntity,
                UpdateDemandDepositAccountWithdrawalResponse.class
        );

        return ResponseEntity.ok(exchange.getBody());
    }
}
