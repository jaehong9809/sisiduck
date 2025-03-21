package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.UpdateDemandDepositAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.request.UpdateDemandDepositAccountDepositRequest;
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

    private final UpdateDemandDepositAccountService updateDemandDepositAccountService;

    @PostMapping("/demandDeposit/updateDemandDepositAccountWithdrawal")
    public ResponseEntity<?> updateDemandDepositAccountWithdrawal(
            @RequestBody UpdateDemandDepositAccountWithdrawalRequest updateDemandDepositAccountWithdrawalRequest
    ){
        return updateDemandDepositAccountService.withdrawal(
                "/demandDeposit/updateDemandDepositAccountWithdrawal",
                updateDemandDepositAccountWithdrawalRequest);
    }

    @PostMapping("/demandDeposit/updateDemandDepositAccountDeposit")
    public ResponseEntity<?> updateDemandDepositAccountDeposit(
            @RequestBody UpdateDemandDepositAccountDepositRequest updateDemandDepositAccountDepositRequest
    ){
        return updateDemandDepositAccountService.deposit(
                "/demandDeposit/updateDemandDepositAccountDeposit",
                updateDemandDepositAccountDepositRequest);
    }
}
