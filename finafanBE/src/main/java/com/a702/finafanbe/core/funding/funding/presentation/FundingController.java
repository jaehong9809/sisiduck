package com.a702.finafanbe.core.funding.funding.presentation;

import com.a702.finafanbe.core.funding.funding.application.FundingService;
import com.a702.finafanbe.core.funding.funding.dto.*;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/fundings")
@RequiredArgsConstructor
public class FundingController {

    private final FundingService fundingService;

    @PostMapping
    public ResponseEntity<?> createFunding(
            @RequestBody CreateFundingRequest request
            // Long userId
    ) {
        Long userId = 1L;
        fundingService.createFunding(request, userId);
        return ResponseUtil.success();
    }

    @GetMapping
    public ResponseEntity<ResponseData<List<GetFundingResponse>>> getFunding(
            @RequestParam(defaultValue = "all") String filter) {
        Long userId = 2L;
        return ResponseUtil.success(fundingService.getFunding(userId, filter));
    }

    @GetMapping("/{fundingGroupId}")
    public ResponseEntity<ResponseData<GetFundingDetailResponse>> getFunding(@PathVariable Long fundingGroupId) {
        Long userId = 2L;
        return ResponseUtil.success(fundingService.getFundingDetail(fundingGroupId, userId));
    }

    @GetMapping("/{fundingGroupId}/join")
    public ResponseEntity<?> joinFunding(@PathVariable Long fundingGroupId) {
        Long userId = 2L;
        fundingService.joinFunding(userId, fundingGroupId);
        return ResponseUtil.success();
    }

    @PostMapping("/{fundingGroupId}/deposit")
    public ResponseEntity<?> participateFunding(@PathVariable Long fundingGroupId, @RequestBody FundingSupportRequest request) {
        Long userId = 2L;
        fundingService.supportFunding(request, userId, fundingGroupId);
        return ResponseUtil.success();
    }

    @PostMapping("/{fundigId}/update")
    public ResponseEntity<?> updateFundingDescription(
            @RequestBody UpdateFundingDescriptionRequest request,
            @PathVariable Long fundingId
    ) {
        return fundingService.updateFundingDescription(request, fundingId);
    }

}
