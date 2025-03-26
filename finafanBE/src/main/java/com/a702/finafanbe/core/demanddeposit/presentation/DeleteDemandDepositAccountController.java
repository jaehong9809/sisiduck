package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.DeleteAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.request.DeleteAccountRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.DeleteAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/demand-deposit")
@RequiredArgsConstructor
public class DeleteDemandDepositAccountController {

    private final DeleteAccountService deleteAccountService;

    @DeleteMapping("/account")
    public ResponseEntity<DeleteAccountResponse> deleteAccount(@RequestBody DeleteAccountRequest deleteAccountRequest){
        return deleteAccountService.deleteAccount(
                "/demandDeposit/deleteDemandDepositAccount",
                deleteAccountRequest
        );
    }
}
