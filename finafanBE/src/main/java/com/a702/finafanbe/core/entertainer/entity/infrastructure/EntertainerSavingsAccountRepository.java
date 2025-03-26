package com.a702.finafanbe.core.entertainer.entity.infrastructure;

import com.a702.finafanbe.core.entertainer.entity.EntertainerSavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntertainerSavingsAccountRepository extends JpaRepository<EntertainerSavingsAccount, Long> {
    boolean findByUserIdAndEntertainerId(Long userId, Long entertainerId);
}
