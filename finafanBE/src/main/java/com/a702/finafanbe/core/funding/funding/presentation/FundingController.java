package com.a702.finafanbe.core.funding.funding.presentation;

import com.a702.finafanbe.core.auth.presentation.annotation.AuthMember;
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

    // 펀딩 생성
    @PostMapping
    public ResponseEntity<?> createFunding(
            @RequestBody CreateFundingRequest request
            //@AuthMember Long userId
    ) {
        Long userId = 1L;
        fundingService.createFunding(request, userId);
        return ResponseUtil.success();
    }

    // 펀딩 목록 조회
    @GetMapping
    public ResponseEntity<ResponseData<List<GetFundingResponse>>> getFunding(
            @RequestParam(defaultValue = "all") String filter
            //@AuthMember Long userId
    ) {
        Long userId = 1L;
        return ResponseUtil.success(fundingService.getFunding(userId, filter));
    }

    // 펀딩 상세 조회
    @GetMapping("/{fundingGroupId}")
    public ResponseEntity<ResponseData<GetFundingDetailResponse>> getFunding(
            @PathVariable Long fundingGroupId
            //@AuthMember Long userId
    ) {
        Long userId = 1L;
        return ResponseUtil.success(fundingService.getFundingDetail(fundingGroupId, userId));
    }

    // 펀딩 가입
    @GetMapping("/{fundingGroupId}/join")
    public ResponseEntity<?> joinFunding(
            @PathVariable Long fundingGroupId
            //@AuthMember Long userId
    ) {
        Long userId = 1L;
        fundingService.joinFunding(userId, fundingGroupId);
        return ResponseUtil.success();
    }

    // 펀딩 입금
    @PostMapping("/{fundingGroupId}/deposit")
    public ResponseEntity<?> participateFunding(
            @PathVariable Long fundingGroupId,
            @RequestBody FundingSupportRequest request
            //@AuthMember Long userId
    ) {
        Long userId = 1L;
        fundingService.supportFunding(request, userId, fundingGroupId);
        return ResponseUtil.success();
    }

    // 펀딩 설명 변경
    @PostMapping("/{fundingId}/update")
    public ResponseEntity<?> updateFundingDescription(
            @RequestBody UpdateFundingDescriptionRequest request,
            @PathVariable Long fundingId
            //@AuthMember Long userId
    ) {
        Long userId = 1L;
        fundingService.updateFundingDescription(request, userId, fundingId);
        return ResponseUtil.success();
    }

    // 펀딩 입금 취소
    @DeleteMapping("/{fundingId}/withdraw/{fundingSupportId}")
    public ResponseEntity<?> withdrawFunding(
            @PathVariable Long fundingId,
            @PathVariable Long fundingSupportId
            //@AuthMember Long userId
    ) {
        Long userId = 1L;
        fundingService.withdrawFunding(userId, fundingSupportId);
        return ResponseUtil.success();
    }

    // 펀딩 중도 해지
    @DeleteMapping("/{fundingId}/cancel")
    public ResponseEntity<?> cancelFunding(
            @PathVariable Long fundingId
            //@AuthMember Long userId
    ) {
        Long userId = 1L;
        fundingService.cancelFunding(userId, fundingId);
        return ResponseUtil.success();
    }

    // 펀딩 종료 해지
    @DeleteMapping("/{fundingId}/terminate")
    public ResponseEntity<?> terminateFunding(
            @PathVariable Long fundingId
            //@AuthMember Long userId
    ) {
        Long userId = 1L;
        fundingService.terminateFunding(userId, fundingId);
        return ResponseUtil.success();
    }


}
