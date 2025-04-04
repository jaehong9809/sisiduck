package com.a702.finafanbe.global.common.financialnetwork.presentation;

import com.a702.finafanbe.core.bank.application.BankService;
import com.a702.finafanbe.core.bank.entity.Bank;
import com.a702.finafanbe.global.common.financialnetwork.dto.request.BankCodesRequest;
import com.a702.finafanbe.global.common.financialnetwork.dto.request.BankCurrencyRequest;
import com.a702.finafanbe.global.common.financialnetwork.dto.response.BankCodesResponse;
import com.a702.finafanbe.global.common.financialnetwork.dto.response.BankCurrencyResponse;
import com.a702.finafanbe.global.common.financialnetwork.dto.response.BankResponseWithId;
import com.a702.finafanbe.global.common.financialnetwork.util.*;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/code")
@RequiredArgsConstructor
public class CommonFinancialNetworkController {

    private final FinancialRequestFactory financialRequestFactory;
    private final ApiClientUtil apiClientUtil;
    private final BankService bankService;

    @GetMapping("/bank")
    public ResponseEntity<ResponseData<List<BankResponseWithId>>> getBankCodes(
        ) {
        List<BankCodesResponse.REC> inquireBankCodes = apiClientUtil.callFinancialNetwork(
                "/bank/inquireBankCodes",
                new BankCodesRequest(
                        financialRequestFactory.createRequestHeader("inquireBankCodes")
                ),
                BankCodesResponse.class
        ).getBody().REC();
        List<Bank> banks = bankService.findAllBanks();
        Map<String,Long> bankCodeToIdMap = banks.stream()
                .collect(Collectors.toMap(Bank::getBankCode, Bank::getBankId));
        return ResponseUtil.success(inquireBankCodes.stream()
                .map(bank -> new BankResponseWithId(
                        bank.bankCode(),
                        bank.bankName(),
                        bankCodeToIdMap.getOrDefault(bank.bankCode(), null)
                ))
                .collect(Collectors.toList()));
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
