package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.dto.response.InquireAccountBalanceResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireAccountHolderNameResponse;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountListResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/demand-deposit")
@RequiredArgsConstructor
public class InquireDemandDepositAccountController {

    private final DemandDepositFacade demandDepositFacade;

    @GetMapping("/account")
    public ResponseEntity<InquireDemandDepositAccountResponse> getDemandDepositAccount(
           @RequestParam String userEmail,
           @RequestParam String accountNo
    ) {
        return demandDepositFacade.getDemandDepositAccount(
                userEmail,
                accountNo
        );
    }

    @GetMapping("/accounts")
    public ResponseEntity<InquireDemandDepositAccountListResponse> getDemandDepositAccountList(
            @RequestParam String userEmail
    ) {
        return demandDepositFacade.getDemandDepositListAccount(userEmail);

    }

    @GetMapping("/account-holder")
    public ResponseEntity<InquireAccountHolderNameResponse> inquireAccountHolderName(
            @RequestParam String userEmail,
            @RequestParam String accountNo
    ){
        return demandDepositFacade.inquireAccountHolderName(
                userEmail,
                accountNo
        );
    }

    @GetMapping("/account-balance")
    public ResponseEntity<InquireAccountBalanceResponse> inquireAccountBalance(
            @RequestParam String userEmail,
            @RequestParam String accountNo
    ){
        return demandDepositFacade.inquireAccountBalance(
                userEmail,
                accountNo
        );
    }
}
