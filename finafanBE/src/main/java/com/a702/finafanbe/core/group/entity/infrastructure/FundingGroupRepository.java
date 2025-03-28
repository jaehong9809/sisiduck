package com.a702.finafanbe.core.group.entity.infrastructure;

import com.a702.finafanbe.core.group.entity.FundingGroup;
import com.a702.finafanbe.core.group.entity.FundingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundingGroupRepository extends JpaRepository<FundingGroup, Long> {
    List<FundingGroup> findAllByStatus(FundingStatus status);
}
