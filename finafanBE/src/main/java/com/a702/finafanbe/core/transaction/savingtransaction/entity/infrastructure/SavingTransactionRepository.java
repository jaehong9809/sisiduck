package com.a702.finafanbe.core.transaction.savingtransaction.entity.infrastructure;

import com.a702.finafanbe.core.transaction.deposittransaction.entity.DepositTransactions;
import com.a702.finafanbe.core.transaction.savingtransaction.entity.SavingTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingTransactionRepository extends JpaRepository<SavingTransactions, Long> {

}
