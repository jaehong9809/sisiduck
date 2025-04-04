package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.RegisterAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.request.RegisterProductRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.RegisterProductResponse;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demand-deposit")
@RequiredArgsConstructor
public class RegisterAccountController {

    private final RegisterAccountService registerAccountService;

    @PostMapping("/product")
    public ResponseEntity<ResponseData<RegisterProductResponse>> registerDemandDeposit(@RequestBody RegisterProductRequest createProductRequest) {
        return ResponseUtil.success(registerAccountService.registerDemandDeposit(
                "/demandDeposit/createDemandDeposit",
                createProductRequest
        ));
    }
}
