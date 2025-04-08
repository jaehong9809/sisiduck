package com.a702.finafanbe.core.funding.group.application;

import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;
import com.a702.finafanbe.core.funding.group.dto.AmountDto;
import com.a702.finafanbe.core.funding.group.dto.CreateGroupBoardRequest;
import com.a702.finafanbe.core.funding.group.dto.GetGroupBoardResponse;
import com.a702.finafanbe.core.funding.group.dto.UpdateGroupBoardRequest;
import com.a702.finafanbe.core.funding.group.entity.*;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.*;
import com.a702.finafanbe.core.user.entity.User;
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
    private final GroupBoardAmountRepository groupBoardAmountRepository;
    private final GroupBoardImageRepository groupBoardImageRepository;

    @Transactional
    public void createBoard(CreateGroupBoardRequest request, User user, Long fundingId) {
        fundingBoardCheck(user.getUserId(), fundingId);
        GroupBoard board = GroupBoard.create(fundingId, request.content());
        groupBoardRepository.save(board);

        List<GroupBoardAmount> amounts = request.amounts().stream()
                .map(dto -> GroupBoardAmount.create(dto, board.getId()))
                .toList();
        groupBoardAmountRepository.saveAll(amounts);

        List<GroupBoardImage> images = request.imageUrl().stream()
                .map(url -> GroupBoardImage.create(url, board.getId()))
                .toList();
        groupBoardImageRepository.saveAll(images);
    }

    public GetGroupBoardResponse getGroupBoard(Long fundingId) {
        GroupBoard board = groupBoardRepository.findByFundingId(fundingId)
                .orElseThrow(() -> new RuntimeException("해당 펀딩에 게시판이 생성되지 않았습니다."));

        List<AmountDto> amounts = groupBoardAmountRepository.findAllByBoardId(board.getId()).stream()
                .map(amount -> AmountDto.of(amount.getContent(), amount.getAmount()))
                .toList();

        List<String> images = groupBoardImageRepository.findAllImageUrlsByBoardId(board.getId());

        return GetGroupBoardResponse.of(board.getContent(), amounts, images);
    }

    @Transactional
    public void updateGroupBoard(UpdateGroupBoardRequest request, User user, Long fundingId) {
        fundingBoardCheck(user.getUserId(), fundingId);
        GroupBoard board = groupBoardRepository.findByFundingId(fundingId).orElseThrow();
        board.update(request.content());

        groupBoardAmountRepository.deleteAllByBoardId(board.getId());
        groupBoardImageRepository.deleteAllByBoardId(board.getId());

        List<GroupBoardAmount> amounts = request.amounts().stream()
                .map(dto -> GroupBoardAmount.create(dto, board.getId()))
                .toList();
        groupBoardAmountRepository.saveAll(amounts);

        List<GroupBoardImage> images = request.imageUrl().stream()
                .map(url -> GroupBoardImage.create(url, board.getId()))
                .toList();
        groupBoardImageRepository.saveAll(images);
    }

    @Transactional
    public void deleteGroupBoard(User user, Long fundingId) {
        fundingBoardCheck(user.getUserId(), fundingId);
        GroupBoard board = groupBoardRepository.findByFundingId(fundingId).orElseThrow(() -> new RuntimeException("펀딩"));
        groupBoardAmountRepository.deleteAllByBoardId(board.getId());
        groupBoardImageRepository.deleteAllByBoardId(board.getId());
        groupBoardRepository.delete(board);
    }

    private void fundingBoardCheck(Long userId, Long fundingId) {
        GroupUser groupUser = groupUserRepository.findByUserIdAndFundingGroupId(userId, fundingId)
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
