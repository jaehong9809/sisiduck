package com.a702.finafanbe.core.funding.group.entity.infrastructure;

import com.a702.finafanbe.core.funding.group.entity.GroupBoardAmount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface GroupBoardAmountRepository extends JpaRepository<GroupBoardAmount, Long> {
    List<GroupBoardAmount> findAllByBoardId(Long id);

    void deleteAllByBoardId(Long boardId);
}
