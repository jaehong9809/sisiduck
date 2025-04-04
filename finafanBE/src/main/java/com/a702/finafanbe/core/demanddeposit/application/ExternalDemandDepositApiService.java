package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountListResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountResponse;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialRequestFactory;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ExternalDemandDepositApiService {

    private final FinancialRequestFactory financialRequestFactory;
    private final ApiClientUtil apiClientUtil;

    public InquireDemandDepositAccountResponse DemandDepositRequest(String path, String userEmail, String accountNo, String apiname) {
        return apiClientUtil.callFinancialNetwork(
                path,
                financialRequestFactory.UserKeyAccountNoRequest(
                        userEmail,
                        accountNo,
                        apiname
                ),
                InquireDemandDepositAccountResponse.class
        ).getBody();
    }

    public <T, R> ResponseEntity<R> DemandDepositRequestWithFactory(
            String path,
            Function<String, T> requestFactory,
            String apiName,
            Class<R> responseClass) {
        T request = requestFactory.apply(apiName);
        return apiClientUtil.callFinancialNetwork(path,request,responseClass);
    }
}
