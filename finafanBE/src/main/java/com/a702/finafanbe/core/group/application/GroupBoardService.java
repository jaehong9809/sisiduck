package com.a702.finafanbe.core.group.application;

import com.a702.finafanbe.core.group.dto.groupboard.CreateGroupBoardRequest;
import com.a702.finafanbe.core.group.entity.GroupBoard;
import com.a702.finafanbe.core.group.entity.infrastructure.GroupBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupBoardService {

    private final GroupBoardRepository groupBoardRepository;

    public void createGroupBoard(CreateGroupBoardRequest request, Long userId, Long groupId) {
        // 펀딩 주최자가 맞는지 확인하는 로직 필요
        GroupBoard groupBoard = GroupBoard.create(groupId, request.getTitle(), request.getContent(), request.getAmount(), request.getImgUrl());
        groupBoardRepository.save(groupBoard);
    }

    public void updateGroupBoard() {

    }

    public void deleteGroupBoard(Long groupBoardId) {
        GroupBoard groupBoard = groupBoardRepository.findById(groupBoardId).orElseThrow();
        groupBoardRepository.delete(groupBoard);
    }
}
