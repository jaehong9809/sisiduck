package com.a702.finafanbe.core.transaction.deposittransaction.entity.infrastructure;

import com.a702.finafanbe.core.transaction.deposittransaction.entity.EntertainerSavingsTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntertainerSavingsTransactionDetailRepository extends JpaRepository<EntertainerSavingsTransactionDetail, Integer> {
    List<EntertainerSavingsTransactionDetail> findByDepositAccountNo(String accountNo);
}
