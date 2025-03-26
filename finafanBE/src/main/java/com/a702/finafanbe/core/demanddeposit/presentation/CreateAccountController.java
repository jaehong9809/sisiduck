package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.dto.response.CreateAccountResponse;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demand-deposit")
@RequiredArgsConstructor
public class CreateAccountController {

    private final DemandDepositFacade demandDepositFacade;

    @PostMapping("/account")
    public ResponseEntity<CreateAccountResponse> createAccount(
            String email,
            String productName
    ){
        return demandDepositFacade.createAccount(
                email,
                productName
        );
    }
}
