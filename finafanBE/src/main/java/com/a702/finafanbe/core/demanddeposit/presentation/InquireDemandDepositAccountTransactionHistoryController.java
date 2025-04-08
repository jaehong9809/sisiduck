package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.auth.presentation.annotation.AuthMember;
import com.a702.finafanbe.core.demanddeposit.dto.request.TransactionHistoriesRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.TransactionHistoryRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.AccountTransactionHistoriesResponse.REC;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountTransactionHistoryResponse;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/demand-deposit")
public class InquireDemandDepositAccountTransactionHistoryController {

    private final DemandDepositFacade demandDepositFacade;

    @GetMapping("/transaction-histories")
    public ResponseEntity<ResponseData<REC>> getDemandDepositTransactionHistories(
        @AuthMember User user,
        @RequestBody TransactionHistoriesRequest transactionHistoryListRequest
    ){
        return ResponseUtil.success(demandDepositFacade.inquireHistories(user, transactionHistoryListRequest));
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
