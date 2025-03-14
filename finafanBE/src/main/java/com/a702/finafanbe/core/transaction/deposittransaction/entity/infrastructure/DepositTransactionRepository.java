package com.a702.finafanbe.core.transaction.deposittransaction.entity.infrastructure;

import com.a702.finafanbe.core.transaction.deposittransaction.entity.DepositTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositTransactionRepository extends JpaRepository<DepositTransactions, Long> {

}
