package com.a702.finafanbe.core.batch.processor;

import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.FundingPendingTransaction;
import com.a702.finafanbe.core.funding.funding.entity.infrastructure.FundingPendingTransactionRepository;
import com.a702.finafanbe.core.funding.group.entity.GroupUser;
import com.a702.finafanbe.core.funding.group.entity.Role;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.GroupUserRepository;
import com.a702.finafanbe.core.savings.entity.SavingsAccount;
import com.a702.finafanbe.core.savings.entity.infrastructure.SavingsAccountRepository;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FundingTransactionProcessor {

    private final FundingPendingTransactionRepository transactionRepository;

    private final GroupUserRepository groupUserRepository;

    private final UserRepository userRepository;

    private final SavingsAccountRepository savingsAccountRepository;

    @Override
    public List<TransactionRequest> process(FundingGroup funding) throws Exception {
        List<FundingPendingTransaction> transactions = transactionRepository.findAllByFundingId(funding.getId());
        GroupUser adminUser = groupUserRepository.findByFundingGroupIdAndRole(funding.getId(), Role.ADMIN)
                .orElseThrow(() -> new RuntimeException("주최 회원을 찾을 수 없습니다."));

        SavingsAccount account = savingsAccountRepository.findById()
    }
}
