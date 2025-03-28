package com.a702.finafanbe.core.savings.entity.infrastructure;

import com.a702.finafanbe.core.savings.entity.FundingApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundingApplicationRepository extends JpaRepository<FundingApplication, Long> {
    List<FundingApplication> findAllByFundingGroupIdAndDeletedAtIsNull(Long fundingGroupId);
}