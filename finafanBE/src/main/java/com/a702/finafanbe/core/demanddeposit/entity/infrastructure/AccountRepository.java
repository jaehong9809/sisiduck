package com.a702.finafanbe.core.demanddeposit.entity.infrastructure;

import com.a702.finafanbe.core.demanddeposit.entity.Account;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountId(Long depositAccountId);

    Optional<Account> findByAccountNo(String accountNo);

    Optional<List<Account>> findByUserId(Long userId);
}
