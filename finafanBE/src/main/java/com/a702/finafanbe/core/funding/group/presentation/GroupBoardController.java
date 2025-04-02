package com.a702.finafanbe.core.funding.group.presentation;

import com.a702.finafanbe.core.funding.group.application.FundingGroupBoardService;
import com.a702.finafanbe.core.funding.group.dto.CreateGroupBoardRequest;
import com.a702.finafanbe.core.funding.group.dto.GetGroupBoardDetailResponse;
import com.a702.finafanbe.core.funding.group.dto.UpdateGroupBoardRequest;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/fundings")
@RequiredArgsConstructor
public class GroupBoardController {

    private final FundingGroupBoardService fundingGroupBoardService;

    // 펀딩 글 생성
    @PostMapping("/{fundingId}/boards/create")
    public ResponseEntity<?> createGroupBoard(
            @RequestBody CreateGroupBoardRequest request,
            @PathVariable Long fundingId
            //@AuthMember Long userId
    ) {
        Long userId = 1L;
        fundingGroupBoardService.createBoard(request, userId, fundingId);
        return ResponseUtil.success();
    }

    // 펀딩 게시판 글 목록 조회
    @GetMapping("/{fundingId}/boards")
    public ResponseEntity<ResponseData<List<GetGroupBoardDetailResponse>>> getGroupBoardDetail(
            @PathVariable Long fundingId
    ) {
        return ResponseUtil.success(fundingGroupBoardService.getGroupBoardDetail(fundingId));
    }

    // 펀딩 게시판 글 수정
    @PutMapping("/{fundingId}/boards/{boardId}")
    public ResponseEntity<?> updateGroupBoard(
            @RequestBody UpdateGroupBoardRequest request,
            @PathVariable Long fundingId,
            @PathVariable Long boardId
            //@AuthMember Long userId
    ) {
        Long userId = 1L;
        fundingGroupBoardService.updateGroupBoard(request, userId, fundingId, boardId);
        return ResponseUtil.success();
    }

    // 펀딩 게시판 글 삭제
    @DeleteMapping("/{fundingId}/boards/{boardId}/delete")
    public ResponseEntity<?> deleteGroupBoard(
            @PathVariable Long fundingId,
            @PathVariable Long boardId
            //@AuthMember Long userId
    ) {
        Long userId = 1L;
        fundingGroupBoardService.deleteGroupBoard(userId, fundingId, boardId);
        return ResponseUtil.success();

    }

}
