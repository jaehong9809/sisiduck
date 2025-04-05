package com.a702.finafanbe.core.funding.group.entity.infrastructure;

import com.a702.finafanbe.core.funding.group.entity.GroupBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupBoardRepository extends JpaRepository<GroupBoard, Long> {
    List<GroupBoard> findAllByFundingGroupId(Long fundingId);

    @Query("SELECT COALESCE(SUM(g.amount), 0) FROM GroupBoard g WHERE g.fundingGroupId = :fundingId")
    Long sumByFundingGroupId(@Param("fundingId") Long fundingId);
}