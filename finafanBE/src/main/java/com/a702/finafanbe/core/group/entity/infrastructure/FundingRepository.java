package com.a702.finafanbe.core.group.entity.infrastructure;

import com.a702.finafanbe.core.group.entity.FundingGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingRepository extends JpaRepository<FundingGroup, Long> {
}
