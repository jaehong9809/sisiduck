package com.a702.finafanbe.core.savings.entity.infrastructure;

import com.a702.finafanbe.core.savings.entity.SavingsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavingsItemRepository extends JpaRepository<SavingsItem, Long> {
    Optional<SavingsItem> findByAccountTypeUniqueNo(String accountTypeUniqueNo);
}
