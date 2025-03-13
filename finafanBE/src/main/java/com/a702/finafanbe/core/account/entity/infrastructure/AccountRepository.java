package com.a702.finafanbe.core.account.entity.infrastructure;

import com.a702.finafanbe.core.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
