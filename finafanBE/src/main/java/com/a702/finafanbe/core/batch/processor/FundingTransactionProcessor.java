package com.a702.finafanbe.core.batch.processor;

import com.a702.finafanbe.core.batch.dto.TransactionRequest;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.FundingPendingTransaction;
import com.a702.finafanbe.core.funding.funding.entity.infrastructure.FundingPendingTransactionRepository;
import com.a702.finafanbe.core.funding.group.entity.GroupUser;
import com.a702.finafanbe.core.funding.group.entity.Role;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.GroupUserRepository;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FundingTransactionProcessor implements ItemProcessor<FundingGroup, List<TransactionRequest>> {

    private final FundingPendingTransactionRepository transactionRepository;

    private final GroupUserRepository groupUserRepository;

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    @Override
    public List<TransactionRequest> process(FundingGroup funding) throws Exception {
        List<FundingPendingTransaction> transactions = transactionRepository.findAllByFundingId(funding.getId());
        GroupUser adminUser = groupUserRepository.findByFundingGroupIdAndRole(funding.getId(), Role.ADMIN)
                .orElseThrow(() -> new RuntimeException("주최 회원을 찾을 수 없습니다."));

        User user = userRepository.findById(adminUser.getId())
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));
        Account representiveAccount = accountRepository.findByAccountId(user.getRepresentAccountId())
                .orElseThrow(() -> new RuntimeException("대표 계좌가 설정되어 있지 않습니다."));

        return transactions.stream()
                .filter(tx -> !isEndofTransaction(tx, funding))
                .map(tx -> {
                    User txUser = userRepository.findById(tx.getUserId()).orElseThrow();
                    Account txAccount = accountRepository.findById(tx.getAccountId()).orElseThrow();
                    return TransactionRequest.of(
                            tx.getId(),
                            txUser.getSocialEmail(),
                            representiveAccount.getAccountNo(),
                            tx.getBalance(),
                            txAccount.getAccountNo(),
                            funding.getName(),
                            funding.getName()
                    );
                })
                .toList();
    }

    private boolean isEndofTransaction(FundingPendingTransaction tx, FundingGroup funding) {
        return funding.getFundingExpiryDate().plusDays(7).isBefore(LocalDate.now());
    }
}
