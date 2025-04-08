package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.dto.response.InquireAccountBalanceResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireAccountHolderNameResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountListResponse.REC;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountResponse;

import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/demand-deposit")
@RequiredArgsConstructor
public class InquireDemandDepositAccountController {

    private final DemandDepositFacade demandDepositFacade;

    @GetMapping("/account")
    public ResponseEntity<ResponseData<InquireDemandDepositAccountResponse.REC>> getDemandDepositAccount(
           @RequestParam String userEmail,
           @RequestParam String accountNo
    ) {
        return ResponseUtil.success(demandDepositFacade.getDemandDepositAccount(
                userEmail,
                accountNo
        ).REC());
    }

    @GetMapping("/accounts")
    public ResponseEntity<ResponseData<List<REC>>> getDemandDepositAccountList(
    ) {

        return ResponseUtil.success(demandDepositFacade
                .getDemandDepositListAccount("lsc7134@naver.com")
                .getBody()
                .REC());
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
