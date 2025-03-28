package com.a702.finafanbe.core.savings.application;

import com.a702.finafanbe.core.savings.dto.apidto.ApiCreateSavingAccountResponse;
import com.a702.finafanbe.core.savings.dto.fundingDto.CreateFundingRequest;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiSavingsAccountService {

    private final ApiClientUtil apiClientUtil;

    private final String CREATE_DEPOSIT = "/demandDeposit/createDemandDepositAccount";

    public String createAccount(CreateFundingRequest request) {
        ResponseEntity<ApiCreateSavingAccountResponse> response = apiClientUtil.callFinancialNetwork(
                CREATE_DEPOSIT,
                request,
                ApiCreateSavingAccountResponse.class
        );
        // api에서 받은 응답 검증 필요
        return response.getBody().getREC().getAccountNo();
    }
}
