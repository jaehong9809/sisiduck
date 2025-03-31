package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.dto.request.ApiCreateAccountResponse;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
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
    public ResponseEntity<ResponseData<ApiCreateAccountResponse>> createAccount(
            String email,
            String productName
    ){
        return ResponseUtil.success(demandDepositFacade.createEntertainerAccount(
                email
        ));
    }
}
