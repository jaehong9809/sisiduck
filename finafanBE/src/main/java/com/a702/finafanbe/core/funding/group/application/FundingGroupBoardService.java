package com.a702.finafanbe.core.funding.group.application;

import com.a702.finafanbe.core.funding.group.dto.groupboard.CreateGroupBoardRequest;
import com.a702.finafanbe.core.funding.group.dto.groupboard.UpdateGroupBoardRequest;
import com.a702.finafanbe.core.funding.group.entity.GroupBoard;
import com.a702.finafanbe.core.funding.group.entity.GroupUser;
import com.a702.finafanbe.core.funding.group.entity.Role;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.FundingGroupRepository;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.GroupBoardRepository;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.GroupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FundingGroupBoardService {

    private final GroupBoardRepository groupBoardRepository;
    private final FundingGroupRepository fundingGroupRepository;
    private final GroupUserRepository groupUserRepository;

    @Transactional
    public void createGroupBoard(CreateGroupBoardRequest request, Long userId, Long fundingId) {
        fundingAdminCheck(fundingId, userId);
        GroupBoard groupBoard = GroupBoard.create(fundingId, request.getTitle(), request.getContent(), request.getAmount(), request.getImgUrl());
        groupBoardRepository.save(groupBoard);
    }

    @Transactional
    public void updateGroupBoard(UpdateGroupBoardRequest request, Long userId, Long fundingId) {
        fundingAdminCheck(fundingId, userId);
    }

    public void deleteGroupBoard(Long groupBoardId, Long userId, Long fundingId) {
        GroupBoard groupBoard = groupBoardRepository.findById(groupBoardId).orElseThrow();
        groupBoardRepository.delete(groupBoard);
    }

    // 해당 유저가 그룹에 속하는지 확인 + 그룹 ADMIN인지 확인
    private void fundingAdminCheck(Long userId, Long fundingId) {
        GroupUser groupUser = groupUserRepository.findByUserIdAndFundingGroupId(fundingId, userId)
                .orElseThrow(() -> new RuntimeException("해당 그룹에 속해있지 않습니다."));
        if (groupUser.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("그룹 게시판에 대한 권한은 방장에게 있습니다.");
        }
    }
}
