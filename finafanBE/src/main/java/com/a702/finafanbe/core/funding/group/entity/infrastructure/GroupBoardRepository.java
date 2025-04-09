package com.a702.finafanbe.core.funding.group.entity.infrastructure;

import com.a702.finafanbe.core.funding.group.entity.GroupBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupBoardRepository extends JpaRepository<GroupBoard, Long> {
    Optional<GroupBoard> findByFundingId(Long fundingId);

    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM GroupBoardAmount b WHERE b.boardId = :boardId")
    Long sumBoardAmount(@Param("boardId") Long boardId);
}