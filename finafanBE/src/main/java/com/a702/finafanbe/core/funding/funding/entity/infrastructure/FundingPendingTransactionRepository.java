package com.a702.finafanbe.core.funding.funding.entity.infrastructure;

import com.a702.finafanbe.core.funding.funding.entity.FundingPendingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FundingPendingTransactionRepository extends JpaRepository<FundingPendingTransaction, Long> {

    List<FundingPendingTransaction> findAllByFundingId(Long fundingId);

    void deleteAllByFundingId(Long fundingId);

    @Query("SELECT COALESCE(SUM(f.balance), 0) FROM FundingPendingTransaction f WHERE f.fundingId = :fundingId AND f.deletedAt IS NULL")
    Long sumByFundingId(@Param("fundingId") Long fundingId);
}