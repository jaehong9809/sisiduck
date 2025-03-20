package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.InquireDemandDepositAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.request.InquireDemandDepositAccountRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountListResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
            @RequestParam String transmissionDate,
            @RequestParam String transmissionTime,
            @RequestParam String institutionCode,
            @RequestParam String fintechAppNo,
            @RequestParam String apiServiceCode,
            @RequestParam String institutionTransactionUniqueNo,
            @RequestParam String apiKey,
            @RequestParam String userKey,
            @RequestParam String accountNo
    ) {
        InquireDemandDepositAccountRequest.RetrieveDemandDepositRequestHeader retrieveDemandDepositRequestHeader = new InquireDemandDepositAccountRequest.RetrieveDemandDepositRequestHeader(
                apiName,
                transmissionDate,
                transmissionTime,
                institutionCode,
                fintechAppNo,
                apiServiceCode,
                institutionTransactionUniqueNo,
                apiKey,
                userKey
        );
        InquireDemandDepositAccountRequest retrieveDemandDepositRequest = new InquireDemandDepositAccountRequest(
                retrieveDemandDepositRequestHeader,
                accountNo
        );
        return inquireDemandDepositAccountService.retrieveDemandDepositAccount(
                retrieveDemandDepositRequest
        );
    }

    @GetMapping("/demandDeposit/inquireDemandDepositAccountList")
    public ResponseEntity<InquireDemandDepositAccountListResponse> getDemandDepositAccountList(
            @RequestParam String apiName,
            @RequestParam String transmissionDate,
            @RequestParam String transmissionTime,
            @RequestParam String institutionCode,
            @RequestParam String fintechAppNo,
            @RequestParam String apiServiceCode,
            @RequestParam String institutionTransactionUniqueNo,
            @RequestParam String apiKey,
            @RequestParam String userKey
    ) {
        InquireDemandDepositAccountRequest.RetrieveDemandDepositRequestHeader retrieveDemandDepositRequestHeader = new InquireDemandDepositAccountRequest.RetrieveDemandDepositRequestHeader(
                apiName,
                transmissionDate,
                transmissionTime,
                institutionCode,
                fintechAppNo,
                apiServiceCode,
                institutionTransactionUniqueNo,
                apiKey,
                userKey
        );
        return inquireDemandDepositAccountService.retrieveDemandDepositAccountList(
                retrieveDemandDepositRequestHeader
        );
    }
}
