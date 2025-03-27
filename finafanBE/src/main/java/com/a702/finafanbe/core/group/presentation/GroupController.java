package com.a702.finafanbe.core.group.presentation;

import com.a702.finafanbe.core.group.application.FundingGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/group")
@RequiredArgsConstructor
public class GroupController {

    private final FundingGroupService fundingGroupService;


}
