package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.UpdateAccountRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.UpdateAccountTransferRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountDepositResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountTransferResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountWithdrawalResponse;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateAccountService {

    private final ApiClientUtil apiClientUtil;

    public ResponseEntity<UpdateDemandDepositAccountWithdrawalResponse> withdrawal(
            String path,
            UpdateAccountRequest updateDemandDepositAccountWithdrawalRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                path,
                updateDemandDepositAccountWithdrawalRequest,
                UpdateDemandDepositAccountWithdrawalResponse.class
        );
    }

    public UpdateDemandDepositAccountDepositResponse deposit(
            String path,
            UpdateAccountRequest updateDemandDepositAccountDepositRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                path,
                updateDemandDepositAccountDepositRequest,
                UpdateDemandDepositAccountDepositResponse.class
        ).getBody();
    }

}
