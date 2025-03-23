package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.CreateAccountService;
import com.a702.finafanbe.core.demanddeposit.application.DeleteAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.request.CreateAccountRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.DeleteAccountRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.CreateAccountResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.DeleteAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/delete-account")
@RequiredArgsConstructor
public class DeleteDemandDepositAccountController {

    private final DeleteAccountService deleteAccountService;

    @PostMapping("demandDeposit/deleteDemandDepositAccount")
    public ResponseEntity<DeleteAccountResponse> deleteAccount(@RequestBody DeleteAccountRequest deleteAccountRequest){
        return deleteAccountService.deleteAccount(
                "/demandDeposit/createDemandDepositAccount",
                deleteAccountRequest
        );
    }
}
