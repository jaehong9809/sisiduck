package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.updatedemanddepositaccountwithdrawalservice;
import com.a702.finafanbe.core.demanddeposit.dto.request.UpdateDemandDepositAccountWithdrawalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/update-demand-deposit-account")
public class UpdateDemandDepositAccountWithdrawalController {

    private final updatedemanddepositaccountwithdrawalservice updateDemandDepositAccountWIthdrawalService;

    @PostMapping
    public ResponseEntity<?> updateDemandDepositAccountWithdrawal(
            @RequestBody UpdateDemandDepositAccountWithdrawalRequest updateDemandDepositAccountWithdrawalRequest
    ){
        return updateDemandDepositAccountWIthdrawalService.withdrawal(updateDemandDepositAccountWithdrawalRequest);
    }
}
