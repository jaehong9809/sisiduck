package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.DeleteAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.response.DeleteAccountResponse;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/demand-deposit")
@RequiredArgsConstructor
public class DeleteDemandDepositAccountController {

    private final DemandDepositFacade demandDepositFacade;

    @DeleteMapping("/account")
    public ResponseEntity<DeleteAccountResponse> deleteAccount(
            @RequestParam String accountNo,
            @RequestParam String refundAccountNo
    ){
        return demandDepositFacade.deleteAccount(
                accountNo,
                refundAccountNo
        );
    }
}
