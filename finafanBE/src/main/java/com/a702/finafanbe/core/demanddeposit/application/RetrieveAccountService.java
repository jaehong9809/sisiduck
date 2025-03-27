package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.RetrieveProductsRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.RetrieveProductsResponse;
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

    public ResponseEntity<RetrieveProductsResponse> retrieveDemandDepositList(
            String path,
            RetrieveProductsRequest retrieveDemandDepositListRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                path,
                retrieveDemandDepositListRequest,
                RetrieveProductsResponse.class
        );
    }

}
