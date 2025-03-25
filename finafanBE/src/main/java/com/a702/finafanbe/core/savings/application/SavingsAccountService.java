package com.a702.finafanbe.core.savings.application;

import com.a702.finafanbe.core.savings.dto.apidto.ApiCreateSavingAccountResponse;
import com.a702.finafanbe.core.savings.dto.CreateSavingAccountRequest;
import com.a702.finafanbe.core.savings.entity.AccountUniqueNo;
import com.a702.finafanbe.core.savings.entity.SavingsAccount;
import com.a702.finafanbe.core.savings.entity.SavingsItem;
import com.a702.finafanbe.core.savings.entity.infrastructure.SavingsAccountRepository;
import com.a702.finafanbe.global.common.entity.BaseEntity;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SavingsAccountService {

    private final ApiClientUtil apiClientUtil;

    private final SavingsAccountRepository savingsAccountRepository;

    private final String CREATE_DEPOSIT = "/demandDeposit/createDemandDepositAccount";

    public void createAccount(Long userId, CreateSavingAccountRequest request) {
        ResponseEntity<ApiCreateSavingAccountResponse> response = apiClientUtil.callFinancialNetwork(
                CREATE_DEPOSIT,
                request,
                ApiCreateSavingAccountResponse.class
        );
        String accountNo = response.getBody().getRec().getAccountNo();

    }


}