package com.a702.finafanbe.core.demanddeposit.entity.infrastructure;

import com.a702.finafanbe.core.demanddeposit.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
