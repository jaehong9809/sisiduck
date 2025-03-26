package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.CreateAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.request.CreateAccountRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.CreateAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demand-deposit")
@RequiredArgsConstructor
public class CreateAccountController {

    private final CreateAccountService createAccountService;

    @PostMapping("/account")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody CreateAccountRequest createAccountRequest){
        return createAccountService.createAccount(
                "/demandDeposit/createDemandDepositAccount",
                createAccountRequest
        );
    }
}
