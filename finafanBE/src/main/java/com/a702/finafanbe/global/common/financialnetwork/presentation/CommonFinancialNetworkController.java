package com.a702.finafanbe.global.common.financialnetwork.presentation;

import com.a702.finafanbe.core.user.dto.response.UserFinancialNetworkResponse;
import com.a702.finafanbe.global.common.financialnetwork.dto.request.BankCodesRequest;
import com.a702.finafanbe.global.common.financialnetwork.dto.request.BankCurrencyRequest;
import com.a702.finafanbe.global.common.financialnetwork.dto.response.BankCodesResponse;
import com.a702.finafanbe.global.common.financialnetwork.dto.response.BankCurrencyResponse;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/code")
@RequiredArgsConstructor
public class CommonFinancialNetworkController {

    private final ApiClientUtil apiClientUtil;

    @PostMapping("/bank/inquireBankCodes")
    public ResponseEntity<ResponseData<BankCodesResponse>> getBankCodes(
        @RequestBody BankCodesRequest bankCodesRequest ) {
        ResponseEntity<BankCodesResponse> bankCodesResponseResponseEntity = apiClientUtil.callFinancialNetwork(
            "/bank/inquireBankCodes",
            bankCodesRequest,
            BankCodesResponse.class
        );
        return ResponseUtil.success();
    }

    @PostMapping("/bank/inquireBankCurrency")
    public ResponseEntity<ResponseData<BankCurrencyResponse>> getBankCodes(
        @RequestBody BankCurrencyRequest bankCurrencyRequest ) {
        ResponseEntity<BankCurrencyResponse> bankCurrencyResponseResponseEntity = apiClientUtil.callFinancialNetwork(
            "/bank/inquireBankCurrency",
            bankCurrencyRequest,
            BankCurrencyResponse.class
        );
        return ResponseUtil.success();
    }
}
