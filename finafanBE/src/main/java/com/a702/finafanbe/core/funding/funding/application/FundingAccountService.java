package com.a702.finafanbe.core.funding.funding.application;

import com.a702.finafanbe.core.funding.funding.dto.CreateFundingRequest;
import com.a702.finafanbe.core.savings.entity.SavingsAccount;
import com.a702.finafanbe.core.savings.entity.SavingsItem;
import com.a702.finafanbe.core.savings.entity.infrastructure.SavingsAccountRepository;
import com.a702.finafanbe.core.savings.entity.infrastructure.SavingsItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FundingAccountService {

    private final SavingsItemRepository savingsItemRepository;

    private final SavingsAccountRepository savingsAccountRepository;

    private final String UNIQUE_NO = "001-1-f5f1f9ee427d47";

    public SavingsAccount createFundingAccount(CreateFundingRequest request, Long userId, String accountNo, String uniqueNo) {

        if (!UNIQUE_NO.equals(uniqueNo)) {
            throw new IllegalArgumentException("Unique No");
        }
        // 생성한 적금 계좌 저장
        SavingsAccount account = SavingsAccount.create(request, userId, uniqueNo, accountNo);
        return savingsAccountRepository.save(account);
    }
}
