package com.a702.finafanbe.core.funding.group.application;

import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;
import com.a702.finafanbe.core.funding.group.dto.CreateGroupBoardRequest;
import com.a702.finafanbe.core.funding.group.dto.GetGroupBoardDetailResponse;
import com.a702.finafanbe.core.funding.group.dto.UpdateGroupBoardRequest;
import com.a702.finafanbe.core.funding.group.entity.GroupBoard;
import com.a702.finafanbe.core.funding.group.entity.GroupUser;
import com.a702.finafanbe.core.funding.group.entity.Role;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.FundingGroupRepository;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.GroupBoardRepository;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.GroupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FundingGroupBoardService {

    private final GroupBoardRepository groupBoardRepository;
    private final GroupUserRepository groupUserRepository;
    private final FundingGroupRepository fundingGroupRepository;

    @Transactional
    public void createBoard(CreateGroupBoardRequest request, Long userId, Long fundingId) {
        fundingBoardCheck(fundingId, userId);
        GroupBoard groupBoard = GroupBoard.create(fundingId, request.getTitle(), request.getContent(), request.getAmount(), request.getImgUrl());
        groupBoardRepository.save(groupBoard);
    }

    public List<GetGroupBoardDetailResponse> getGroupBoardDetail(Long fundingId) {
        List<GroupBoard> boards = groupBoardRepository.findAllByFundingGroupId(fundingId);
        return boards.stream().
                map(b -> GetGroupBoardDetailResponse.of(
                        b.getId(),
                        b.getTitle(),
                        b.getContent(),
                        b.getAmount(),
                        b.getImageUrl()
                ))
                .toList();
    }

    @Transactional
    public void updateGroupBoard(UpdateGroupBoardRequest request, Long userId, Long fundingId, Long boardId) {
        fundingBoardCheck(fundingId, userId);
        GroupBoard board = groupBoardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
        board.updateBoard(request);
    }

    public void deleteGroupBoard(Long userId, Long fundingId, Long groupBoardId) {
        GroupBoard groupBoard = groupBoardRepository.findById(groupBoardId).orElseThrow();
        groupBoardRepository.delete(groupBoard);
    }

    // 해당 유저가 그룹에 속하는지 확인 + 그룹 ADMIN인지 확인 + funding이 끝났는지 확인
    private void fundingBoardCheck(Long userId, Long fundingId) {
        GroupUser groupUser = groupUserRepository.findByUserIdAndFundingGroupId(fundingId, userId)
                .orElseThrow(() -> new RuntimeException("해당 그룹에 속해있지 않습니다."));
        if (!groupUser.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("그룹 게시판에 대한 권한은 방장에게 있습니다.");
        }
        FundingGroup funding = fundingGroupRepository.findById(fundingId)
                .orElseThrow(() -> new RuntimeException("해당 펀딩이 존재하지 않습니다."));
        if (!funding.getStatus().equals(FundingStatus.SUCCESS)) {
            throw new RuntimeException("성공하지 않은 펀딩의 게시판은 접근할 수 없습니다.");
        }

    }
}
