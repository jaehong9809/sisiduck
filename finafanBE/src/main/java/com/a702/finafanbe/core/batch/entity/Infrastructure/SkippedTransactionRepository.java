package com.a702.finafanbe.core.batch.entity.Infrastructure;

import com.a702.finafanbe.core.batch.entity.SkippedTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkippedTransactionRepository extends JpaRepository<SkippedTransaction, Long> {

    List<SkippedTransaction> findByRe
}
