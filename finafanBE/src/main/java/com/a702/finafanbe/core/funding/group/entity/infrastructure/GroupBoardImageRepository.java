package com.a702.finafanbe.core.funding.group.entity.infrastructure;

import com.a702.finafanbe.core.funding.group.entity.GroupBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupBoardImageRepository extends JpaRepository<GroupBoardImage, Long> {
    List<GroupBoardImage> findAllByBoardId(Long boardId);

    @Query("SELECT g.imageUrl FROM GroupBoardImage g WHERE g.boardId = :boardId")
    List<String> findAllImageUrlsByBoardId(@Param("boardId") Long boardId);

    void deleteAllByBoardId(Long boardId);
}
