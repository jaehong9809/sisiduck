package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.UpdateDemandDepositAccountTransferLimitRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountTransferLimitResponse;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateTransferLimitService {

    private final ApiClientUtil apiClientUtil;

    public ResponseEntity<UpdateDemandDepositAccountTransferLimitResponse> updateLimit(
        String path,
        UpdateDemandDepositAccountTransferLimitRequest updateDemandDepositAccountTransferLimitRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
            path,
            updateDemandDepositAccountTransferLimitRequest,
            UpdateDemandDepositAccountTransferLimitResponse.class
        );
    }
}
