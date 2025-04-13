package com.a702.finafanbe.core.transaction.deposittransaction.entity.infrastructure;

import com.a702.finafanbe.core.transaction.deposittransaction.entity.EntertainerSavingsTransactionDetail;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntertainerSavingsTransactionDetailRepository extends JpaRepository<EntertainerSavingsTransactionDetail, Integer>, EntertainerSavingsTransactionDetailRepositoryCustom {

    Optional<List<EntertainerSavingsTransactionDetail>> findByDepositAccountId(Long accountId);

}
