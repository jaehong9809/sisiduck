package com.a702.finafanbe.core.demanddeposit.entity.infrastructure;

import com.a702.finafanbe.core.demanddeposit.entity.EntertainerSavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntertainerSavingsAccountRepository extends JpaRepository<EntertainerSavingsAccount, Long> {

    boolean existsByUserIdAndEntertainerId(Long userId, Long entertainerId);

    List<EntertainerSavingsAccount> findByUserId(Long userId);

    EntertainerSavingsAccount findByDepositAccountId(Long savingAccountId);
}
