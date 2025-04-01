package com.a702.finafanbe.core.funding.funding.entity.infrastructure;

import com.a702.finafanbe.core.funding.funding.entity.FundingSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FundingSupportRepository extends JpaRepository<FundingSupport, Long> {
    List<FundingSupport> findAllByFundingGroupIdAndDeletedAtIsNull(Long fundingGroupId);

    void deleteAllByFundingGroupId(Long fundingId);

    @Query("SELECT COALESCE(SUM(f.balance), 0) FROM FundingSupport f WHERE f.fundingGroupId = :fundingId AND f.deletedAt IS NULL")
    Long sumByFundingGroupId(@Param("fundingId") Long fundingId);

}