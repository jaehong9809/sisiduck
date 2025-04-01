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

    public SavingsAccount createFundingAccount(CreateFundingRequest request, Long userId, String accountNo) {

        // 적금 상품 찾아오기

        SavingsItem item = savingsItemRepository.findByAccountTypeUniqueNo("2222").orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다."));

        // 생성한 적금 계좌 저장
        SavingsAccount account = SavingsAccount.builder()
                .userId(userId)
                .savingsItemId(item.getSavingsItemId())
                .accountNo(accountNo)
                .accountNickname(request.accountNickname())
                .balance(0L)
                .accountExpiryDate(LocalDateTime.now().plusMonths(6))
                .build();

        return savingsAccountRepository.save(account);
    }

}
