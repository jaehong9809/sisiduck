package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.UpdateAccountTransferLimitRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountTransferLimitResponse;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateTransferLimitService {

    private final ApiClientUtil apiClientUtil;

    public UpdateDemandDepositAccountTransferLimitResponse updateLimit(
        String path,
        UpdateAccountTransferLimitRequest updateDemandDepositAccountTransferLimitRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
            path,
            updateDemandDepositAccountTransferLimitRequest,
            UpdateDemandDepositAccountTransferLimitResponse.class
        ).getBody();
    }
}
