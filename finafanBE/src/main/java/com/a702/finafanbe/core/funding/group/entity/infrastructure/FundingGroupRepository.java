package com.a702.finafanbe.core.funding.group.entity.infrastructure;

import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FundingGroupRepository extends JpaRepository<FundingGroup, Long> {

    List<FundingGroup> findAllByStatus(FundingStatus status);
    @Query("SELECT f FROM FundingGroup f WHERE f.fundingExpiryDate < :now AND f.status = :status")
    List<FundingGroup> findExpiredFunding(@Param("now")LocalDateTime now, @Param("status") FundingStatus status);
}