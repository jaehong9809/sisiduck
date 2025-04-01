package com.a702.finafanbe.core.funding.group.presentation;

import com.a702.finafanbe.core.funding.group.application.FundingGroupService;
import com.a702.finafanbe.core.funding.group.dto.groupboard.CreateGroupBoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/fundings/groups")
@RequiredArgsConstructor
public class GroupController {

    private final FundingGroupService fundingGroupService;

    @PostMapping
    public void createGroupBoard(
            @RequestBody CreateGroupBoardRequest request,
            Long userId
    ) {
        return groupBoardService.createBoard();
    }
}
