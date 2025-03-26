package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.InquireDemandDepositAccountTransactionHistoryService;
import com.a702.finafanbe.core.demanddeposit.dto.request.InquireDemandDepositAccountTransactionHistoryListRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.InquireDemandDepositAccountTransactionHistoryRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountTransactionHistoryListResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountTransactionHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/demand-deposit-transaction")
public class InquireDemandDepositAccountTransactionHistoryController {

    private final InquireDemandDepositAccountTransactionHistoryService inquireDemandDepositAccountTransactionHistoryService;

    @GetMapping("/transaction-histories")
    public ResponseEntity<InquireDemandDepositAccountTransactionHistoryListResponse> getDemandDepositTransactionHistories(
        @RequestBody InquireDemandDepositAccountTransactionHistoryListRequest inquireDemandDepositAccountTransactionHistoryListRequest
    ){
        return inquireDemandDepositAccountTransactionHistoryService.inquireHistories(
            "/demandDeposit/inquireTransactionHistoryList",
            inquireDemandDepositAccountTransactionHistoryListRequest
        );
    }

    @GetMapping("/transaction-history")
    public ResponseEntity<InquireDemandDepositAccountTransactionHistoryResponse> getDemandDepositTransactionHistory(
        @RequestBody InquireDemandDepositAccountTransactionHistoryRequest inquireDemandDepositAccountTransactionHistoryRequest
    ){
        return inquireDemandDepositAccountTransactionHistoryService.inquireHistory(
            "/demandDeposit/inquireTransactionHistory",
            inquireDemandDepositAccountTransactionHistoryRequest
        );
    }
}
