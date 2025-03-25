package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.InquireDemandDepositAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.request.InquireAccountBalanceRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.InquireAccountHolderNameRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.InquireDemandDepositAccountListRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.InquireDemandDepositAccountRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireAccountBalanceResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireAccountHolderNameResponse;
import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeaderIncludeUserKey;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountListResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inquire-demand-deposit-account")
@RequiredArgsConstructor
public class InquireDemandDepositAccountController {

    private final InquireDemandDepositAccountService inquireDemandDepositAccountService;

    @GetMapping("/demandDeposit/inquireDemandDepositAccount")
    public ResponseEntity<InquireDemandDepositAccountResponse> getDemandDepositAccount(
            @RequestParam String apiName,
            @RequestParam String institutionCode,
            @RequestParam String fintechAppNo,
            @RequestParam String apiServiceCode,
            @RequestParam String institutionTransactionUniqueNo,
            @RequestParam String apiKey,
            @RequestParam String userKey,
            @RequestParam String accountNo
    ) {
        BaseRequestHeaderIncludeUserKey inquireDemandDepositRequestHeader = BaseRequestHeaderIncludeUserKey.builder()
            .apiName(apiName)
            .transmissionDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
            .transmissionTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss")))
            .institutionCode(institutionCode)
            .fintechAppNo(fintechAppNo)
            .apiServiceCode(apiServiceCode)
            .institutionTransactionUniqueNo(institutionTransactionUniqueNo)
            .apiKey(apiKey)
            .userKey(userKey)
            .build();
        InquireDemandDepositAccountRequest inquireDemandDepositAccountRequest = new InquireDemandDepositAccountRequest(
                inquireDemandDepositRequestHeader,
                accountNo
        );
        return inquireDemandDepositAccountService.retrieveDemandDepositAccount(
                "/demandDeposit/inquireDemandDepositAccount",
                inquireDemandDepositAccountRequest
        );
    }

    @GetMapping("/demandDeposit/inquireDemandDepositAccountList")
    public ResponseEntity<InquireDemandDepositAccountListResponse> getDemandDepositAccountList(
            @RequestParam String apiName,
            @RequestParam String institutionCode,
            @RequestParam String fintechAppNo,
            @RequestParam String apiServiceCode,
            @RequestParam String institutionTransactionUniqueNo,
            @RequestParam String apiKey,
            @RequestParam String userKey
    ) {
        BaseRequestHeaderIncludeUserKey inquireDemandDepositRequestHeader = BaseRequestHeaderIncludeUserKey.builder()
            .apiName(apiName)
            .transmissionDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
            .transmissionTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss")))
            .institutionCode(institutionCode)
            .fintechAppNo(fintechAppNo)
            .apiServiceCode(apiServiceCode)
            .institutionTransactionUniqueNo(institutionTransactionUniqueNo)
            .apiKey(apiKey)
            .userKey(userKey)
            .build();
        InquireDemandDepositAccountListRequest inquireDemandDepositAccountListRequest = new InquireDemandDepositAccountListRequest(
                inquireDemandDepositRequestHeader
        );
        return inquireDemandDepositAccountService.retrieveDemandDepositAccountList(
                "/demandDeposit/inquireDemandDepositAccountList",
                inquireDemandDepositAccountListRequest
        );
    }

    @PostMapping("/demandDeposit/inquireDemandDepositAccountHolderName")
    public ResponseEntity<InquireAccountHolderNameResponse> inquireAccountHolderName(@RequestBody InquireAccountHolderNameRequest inquireAccountHolderNameRequest){
        return inquireDemandDepositAccountService.inquireAccountHolderName(
            "/demandDeposit/inquireDemandDepositAccountHolderName",
            inquireAccountHolderNameRequest
        );
    }

    @PostMapping("/demandDeposit/inquireDemandDepositAccountBalance")
    public ResponseEntity<InquireAccountBalanceResponse> inquireAccountBalance(@RequestBody InquireAccountBalanceRequest inquireAccountBalanceRequest){
        return inquireDemandDepositAccountService.inquireBalanceName(
            "/demandDeposit/inquireDemandDepositAccountBalance",
            inquireAccountBalanceRequest
        );
    }
}
