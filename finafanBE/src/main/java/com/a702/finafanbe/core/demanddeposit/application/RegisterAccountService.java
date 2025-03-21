package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.RegisterDemandDepositRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.RegisterDemandDepositResponse;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterAccountService {

    private final ApiClientUtil apiClientUtil;
    private final AccountRepository accountRepository;

    public ResponseEntity<RegisterDemandDepositResponse> registerDemandDeposit(
            String path,
            RegisterDemandDepositRequest registerDemandDepositRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                path,
                registerDemandDepositRequest,
                RegisterDemandDepositResponse.class
        );
    }
}
