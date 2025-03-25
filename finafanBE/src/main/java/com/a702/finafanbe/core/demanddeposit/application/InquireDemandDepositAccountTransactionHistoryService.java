package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.InquireDemandDepositAccountTransactionHistoryListRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.InquireDemandDepositAccountTransactionHistoryRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountTransactionHistoryListResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountTransactionHistoryResponse;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquireDemandDepositAccountTransactionHistoryService {

    private final ApiClientUtil apiClientUtil;

    public ResponseEntity<InquireDemandDepositAccountTransactionHistoryListResponse> inquireHistories(
        String path,
        InquireDemandDepositAccountTransactionHistoryListRequest inquireDemandDepositAccountTransactionHistoryListRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
            path,
            inquireDemandDepositAccountTransactionHistoryListRequest,
            InquireDemandDepositAccountTransactionHistoryListResponse.class
        );
    }

    public ResponseEntity<InquireDemandDepositAccountTransactionHistoryResponse> inquireHistory(
        String path,
        InquireDemandDepositAccountTransactionHistoryRequest inquireDemandDepositAccountTransactionHistoryRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
            path,
            inquireDemandDepositAccountTransactionHistoryRequest,
            InquireDemandDepositAccountTransactionHistoryResponse.class
        );
    }
}
