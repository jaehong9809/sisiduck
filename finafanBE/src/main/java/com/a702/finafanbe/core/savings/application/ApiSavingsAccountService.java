package com.a702.finafanbe.core.savings.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.ApiCreateAccountResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.CreateAccountResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.DeleteAccountResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.RetrieveProductsResponse;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.demanddeposit.entity.EntertainerSavingsAccount;
import com.a702.finafanbe.core.savings.dto.apidto.ApiCreateSavingAccountResponse;
import com.a702.finafanbe.core.funding.funding.dto.CreateFundingRequest;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialNetworkUtil;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialRequestFactory;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ApiSavingsAccountService {

    private final UserRepository userRepository;

    private final ApiClientUtil apiClientUtil;

    private final FinancialRequestFactory financialRequestFactory;

    private final String CREATE_DEPOSIT = "/demandDeposit/createDemandDepositAccount";

    private final String UNIQUE_NO = "001-1-f5f1f9ee427d47";

    // 계좌 생성
    public ApiCreateAccountResponse createFundingAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));
        //System.out.println("1111");

        CreateAccountResponse.REC createDemandDepositAccount = apiClientUtil.callFinancialNetwork(
                CREATE_DEPOSIT,
                financialRequestFactory.UserKeyAccountTypeUniqueNoRequest(
                        user.getSocialEmail(),
                        UNIQUE_NO,
                        "createDemandDepositAccount"
                ),
                CreateAccountResponse.class
        ).getBody().REC();

        //System.out.println("2222");
        return ApiCreateAccountResponse.of(
                user.getUserId(),
                createDemandDepositAccount.getAccountNo(),
                createDemandDepositAccount.getBankCode(),
                createDemandDepositAccount.getCurrency().getCurrency(),
                UNIQUE_NO
        );
    }
}
