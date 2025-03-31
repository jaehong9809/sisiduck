package com.a702.finafanbe.global.common.financialnetwork.presentation;

import com.a702.finafanbe.global.common.financialnetwork.dto.request.BankCodesRequest;
import com.a702.finafanbe.global.common.financialnetwork.dto.request.BankCurrencyRequest;
import com.a702.finafanbe.global.common.financialnetwork.dto.response.BankCodesResponse;
import com.a702.finafanbe.global.common.financialnetwork.dto.response.BankCurrencyResponse;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialNetworkUtil;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialRequestFactory;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/code")
@RequiredArgsConstructor
public class CommonFinancialNetworkController {

    private final FinancialRequestFactory financialRequestFactory;
    private final ApiClientUtil apiClientUtil;

    @GetMapping("/bank")
    public ResponseEntity<ResponseData<List<BankCodesResponse.REC>>> getBankCodes(
        ) {
        ResponseEntity<BankCodesResponse> bankCodesResponseResponseEntity = apiClientUtil.callFinancialNetwork(
            "/bank/inquireBankCodes",
            new BankCodesRequest(
                    financialRequestFactory.createRequestHeader("inquireBankCodes")
            ),
            BankCodesResponse.class
        );
        return ResponseUtil.success(bankCodesResponseResponseEntity.getBody().REC());
    }

    @GetMapping("/currency")
    public ResponseEntity<ResponseData<List<BankCurrencyResponse.REC>>> getCurrencyCodes() {
        ResponseEntity<BankCurrencyResponse> bankCurrencyResponseResponseEntity = apiClientUtil.callFinancialNetwork(
            "/bank/inquireBankCurrency",
            new BankCurrencyRequest(
                    financialRequestFactory.createRequestHeader("inquireBankCurrency")
            ),
            BankCurrencyResponse.class
        );
        return ResponseUtil.success(bankCurrencyResponseResponseEntity.getBody().REC());
    }
}
