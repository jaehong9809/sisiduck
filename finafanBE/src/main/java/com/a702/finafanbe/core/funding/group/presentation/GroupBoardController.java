package com.a702.finafanbe.core.funding.group.presentation;

import com.a702.finafanbe.core.auth.presentation.annotation.AuthMember;
import com.a702.finafanbe.core.funding.group.application.FundingGroupBoardService;
import com.a702.finafanbe.core.funding.group.dto.CreateGroupBoardRequest;
import com.a702.finafanbe.core.funding.group.dto.GetGroupBoardResponse;
import com.a702.finafanbe.core.funding.group.dto.UpdateGroupBoardRequest;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
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
    private final UserRepository userRepository;

    @PostMapping("/{fundingId}/board/create")
    public ResponseEntity<?> createGroupBoard(
            @RequestBody CreateGroupBoardRequest request,
            @PathVariable Long fundingId,
            @AuthMember User user
    ) {
        fundingGroupBoardService.createBoard(request, user, fundingId);
        return ResponseUtil.success();
    }

    @GetMapping("/{fundingId}/board")
    public ResponseEntity<ResponseData<GetGroupBoardResponse>> getGroupBoard(
            @PathVariable Long fundingId,
            @AuthMember User user
    ) {
        return ResponseUtil.success(fundingGroupBoardService.getGroupBoard(fundingId));
    }

    @PutMapping("/{fundingId}/board/update")
    public ResponseEntity<?> updateGroupBoard(
            @RequestBody UpdateGroupBoardRequest request,
            @PathVariable Long fundingId,
            @AuthMember User user
    ) {
        fundingGroupBoardService.updateGroupBoard(request, user, fundingId);
        return ResponseUtil.success();
    }

    @DeleteMapping("/{fundingId}/board/delete")
    public ResponseEntity<?> deleteGroupBoard(
            @PathVariable Long fundingId,
            @AuthMember User user
    ) {
        fundingGroupBoardService.deleteGroupBoard(user, fundingId);
        return ResponseUtil.success();
    }
}
