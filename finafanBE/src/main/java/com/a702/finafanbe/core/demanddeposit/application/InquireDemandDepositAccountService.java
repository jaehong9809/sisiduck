package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.InquireDemandDepositAccountListRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.InquireDemandDepositAccountRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountListResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountResponse;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquireDemandDepositAccountService {

    private final ApiClientUtil apiClientUtil;
    private final AccountRepository accountRepository;

    public ResponseEntity<InquireDemandDepositAccountResponse> retrieveDemandDepositAccount(
            String path,
            InquireDemandDepositAccountRequest retrieveDemandDepositRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                path,
                retrieveDemandDepositRequest,
                InquireDemandDepositAccountResponse.class
        );
    }

    public ResponseEntity<InquireDemandDepositAccountListResponse> retrieveDemandDepositAccountList(
            String path,
            InquireDemandDepositAccountListRequest inquireDemandDepositAccountListRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                path,
                inquireDemandDepositAccountListRequest,
                InquireDemandDepositAccountListResponse.class
        );
    }
}

