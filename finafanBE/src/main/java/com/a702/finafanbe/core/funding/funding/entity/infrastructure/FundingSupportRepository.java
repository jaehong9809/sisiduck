package com.a702.finafanbe.core.funding.funding.entity.infrastructure;

import com.a702.finafanbe.core.funding.funding.entity.FundingSupport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundingSupportRepository extends JpaRepository<FundingSupport, Long> {
    List<FundingSupport> findAllByFundingGroupIdAndDeletedAtIsNull(Long fundingGroupId);
}