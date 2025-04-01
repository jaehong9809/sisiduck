package com.a702.finafanbe.core.funding.group.entity.infrastructure;

import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundingGroupRepository extends JpaRepository<FundingGroup, Long> {
    List<FundingGroup> findAllByStatus(FundingStatus status);
}
