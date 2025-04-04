package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.RegisterProductRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.RegisterProductResponse;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterAccountService {

    private final ApiClientUtil apiClientUtil;

    public RegisterProductResponse registerDemandDeposit(
            String path,
            RegisterProductRequest registerDemandDepositRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                path,
                registerDemandDepositRequest,
                RegisterProductResponse.class
        ).getBody();
    }
}
