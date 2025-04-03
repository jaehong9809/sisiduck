package com.a702.finafanbe.core.demanddeposit.entity.infrastructure;

import com.a702.finafanbe.core.demanddeposit.entity.EntertainerSavingsAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntertainerSavingsAccountRepository extends JpaRepository<EntertainerSavingsAccount, Long> {

    Optional<List<EntertainerSavingsAccount>> findByUserId(Long userId);

    Optional<EntertainerSavingsAccount> findByDepositAccountId(Long savingAccountId);

    Optional<EntertainerSavingsAccount> findByWithdrawalAccountId(Long savingAccountId);

    boolean existsByWithdrawalAccountId(Long savingAccountId);

    boolean existsByDepositAccountId(Long accountId);

    boolean existsByUserIdAndEntertainerIdAndDeletedAtNull(Long userId, Long entertainerId);

    List<EntertainerSavingsAccount> findByEntertainerId(Long entertainerId);
}
