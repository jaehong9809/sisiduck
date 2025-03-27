package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.InquireDemandDepositAccountTransactionHistoryService;
import com.a702.finafanbe.core.demanddeposit.dto.request.TransactionHistoriesRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.TransactionHistoryRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.AccountTransactionHistoriesResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountTransactionHistoryResponse;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/demand-deposit-transaction")
public class InquireDemandDepositAccountTransactionHistoryController {

    private final DemandDepositFacade demandDepositFacade;
    private final InquireDemandDepositAccountTransactionHistoryService inquireDemandDepositAccountTransactionHistoryService;

    @PostMapping("/demandDeposit/inquireTransactionHistoryList")
    public ResponseEntity<AccountTransactionHistoriesResponse> getDemandDepositTransactionHistories(
        @RequestBody TransactionHistoriesRequest transactionHistoryListRequest
    ){
        return demandDepositFacade.inquireHistories(
                transactionHistoryListRequest
        );
    }

    @GetMapping("/transaction-history")
    public ResponseEntity<InquireDemandDepositAccountTransactionHistoryResponse> getDemandDepositTransactionHistory(
        @RequestBody TransactionHistoryRequest transactionHistoryRequest
    ){
        return demandDepositFacade.inquireHistory(
                transactionHistoryRequest
        );
    }
}
