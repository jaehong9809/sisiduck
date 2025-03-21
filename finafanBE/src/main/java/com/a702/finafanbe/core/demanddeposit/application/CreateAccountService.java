package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.CreateAccountRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.CreateAccountResponse;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAccountService {

    private final ApiClientUtil apiClientUtil;

    public ResponseEntity<CreateAccountResponse> createAccount(
            String path,
            CreateAccountRequest createAccountRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
                path,
                createAccountRequest,
                CreateAccountResponse.class
        );
    }
}
