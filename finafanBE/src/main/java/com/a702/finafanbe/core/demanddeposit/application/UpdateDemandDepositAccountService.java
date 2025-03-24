package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.UpdateDemandDepositAccountDepositRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.UpdateDemandDepositAccountTransferRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.UpdateDemandDepositAccountWithdrawalRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountDepositResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountTransferResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountWithdrawalResponse;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateDemandDepositAccountService {

    private final ApiClientUtil apiClientUtil;

    public ResponseEntity<UpdateDemandDepositAccountWithdrawalResponse> withdrawal(
            String path,
            UpdateDemandDepositAccountWithdrawalRequest updateDemandDepositAccountWithdrawalRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                path,
                updateDemandDepositAccountWithdrawalRequest,
                UpdateDemandDepositAccountWithdrawalResponse.class
        );
    }

    public ResponseEntity<UpdateDemandDepositAccountDepositResponse> deposit(
            String path,
            UpdateDemandDepositAccountDepositRequest updateDemandDepositAccountDepositRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                path,
                updateDemandDepositAccountDepositRequest,
                UpdateDemandDepositAccountDepositResponse.class
        );
    }

    public ResponseEntity<UpdateDemandDepositAccountTransferResponse> transfer(
        String path,
        UpdateDemandDepositAccountTransferRequest updateDemandDepositAccountTransferRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
            path,
            updateDemandDepositAccountTransferRequest,
            UpdateDemandDepositAccountTransferResponse.class
        );
    }
}
