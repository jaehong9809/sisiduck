package com.a702.finafanbe.core.savings.entity.infrastructure;

import com.a702.finafanbe.core.savings.entity.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsRepository extends JpaRepository<Savings, Long> {
}
