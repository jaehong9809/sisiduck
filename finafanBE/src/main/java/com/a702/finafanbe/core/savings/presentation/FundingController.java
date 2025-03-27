package com.a702.finafanbe.core.savings.presentation;

import com.a702.finafanbe.core.savings.application.funding.FundingService;
import com.a702.finafanbe.core.savings.dto.fundingDto.CreateFundingRequest;
import com.a702.finafanbe.core.savings.dto.fundingDto.GetFundingDetailResponse;
import com.a702.finafanbe.core.savings.dto.fundingDto.GetFundingResponse;
import com.a702.finafanbe.core.savings.dto.fundingDto.ParticipateFundingRequest;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import jakarta.validation.Valid;
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
        Long userId = 1L;
        return ResponseUtil.success(fundingService.getFunding(userId, filter));
    }

    @GetMapping("/{fundingGroupId}")
    public ResponseEntity<ResponseData<GetFundingDetailResponse>> getFunding(@PathVariable Long fundingGroupId) {
        Long userId = 1L;
        return ResponseUtil.success(fundingService.getFundingDetail(fundingGroupId, userId));
    }

    @PostMapping("/{fundingGroupId}")
    public ResponseEntity<?> joinFunding(@PathVariable Long fundingGroupId) {
        Long userId = 2L;
        fundingService.joinFunding(userId, fundingGroupId);
        return ResponseUtil.success();
    }

    @PostMapping("/{fundingGroupId}/deposit")
    public ResponseEntity<?> participateFunding(@PathVariable Long fundingGroupId, @RequestBody ParticipateFundingRequest request) {
        Long userId = 1L;
        fundingService.participateFunding(request, userId, fundingGroupId);
        return ResponseUtil.success();
    }

}
