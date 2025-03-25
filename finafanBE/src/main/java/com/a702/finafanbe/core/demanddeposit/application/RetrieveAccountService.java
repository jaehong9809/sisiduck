package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.RetrieveDemandDepositListRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.RetrieveDemandDepositListResponse;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveAccountService {

    private final ApiClientUtil apiClientUtil;
    private final AccountRepository accountRepository;

    public ResponseEntity<RetrieveDemandDepositListResponse> retrieveDemandDepositList(
            String path,
            RetrieveDemandDepositListRequest retrieveDemandDepositListRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                path,
                retrieveDemandDepositListRequest,
                RetrieveDemandDepositListResponse.class
        );
    }

}
