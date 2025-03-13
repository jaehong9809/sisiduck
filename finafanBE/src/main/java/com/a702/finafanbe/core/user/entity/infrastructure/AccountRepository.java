package com.a702.finafanbe.core.user.entity.infrastructure;

import com.a702.finafanbe.core.user.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
