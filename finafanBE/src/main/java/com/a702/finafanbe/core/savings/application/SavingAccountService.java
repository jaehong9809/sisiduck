package com.a702.finafanbe.core.savings.application;

import com.a702.finafanbe.core.savings.dto.APIdto.APICreateAccountRequest;
import com.a702.finafanbe.core.savings.dto.APIdto.APICreateAccountResponse;
import com.a702.finafanbe.core.savings.dto.CreateSavingAccountRequest;
import com.a702.finafanbe.core.savings.entity.infrastructure.SavingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavingAccountService {

    private final SavingsRepository savingsRepository;
    private final APIClientUtil apiClientUtil;

    private final String CREATE_DEPOSIT = "/demandDeposit/createDemandDepositAccount";
    // 적금, 펀딩의 공통적인 기능
    // 계좌 개설
    // 입금
    // 계좌 별명 변경
    public void createAccount(CreateSavingAccountRequest request) {
        APICreateAccountRequest APIrequest = APICreateAccountRequest.of(request);
        ResponseEntity<APICreateAccountResponse> response = apiClientUtil.callFinancialNetwork(CREATE_DEPOSIT, APIrequest, APICreateAccountResponse.class);
    }

    public void depositMoney() {

    }

    public void updateAccountName() {

    }

}
