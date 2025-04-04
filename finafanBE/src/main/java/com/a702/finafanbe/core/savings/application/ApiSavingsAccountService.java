package com.a702.finafanbe.core.savings.application;

import com.a702.finafanbe.core.savings.dto.apidto.ApiCreateSavingAccountResponse;
import com.a702.finafanbe.core.savings.dto.fundingDto.CreateFundingRequest;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.a702.finafanbe.global.common.financialnetwork.util.ApiConstants.CREATE_DEPOSIT;

@Service
@RequiredArgsConstructor
public class ApiSavingsAccountService {

    private final ApiClientUtil apiClientUtil;

    public String createAccount(CreateFundingRequest request) {
        return apiClientUtil.callFinancialNetwork(
                CREATE_DEPOSIT,
                request,
                ApiCreateSavingAccountResponse.class
        ).getBody().getREC().getAccountNo();
    }
}
