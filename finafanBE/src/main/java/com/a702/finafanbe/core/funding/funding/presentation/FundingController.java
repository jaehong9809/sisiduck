package com.a702.finafanbe.core.funding.funding.presentation;

import com.a702.finafanbe.core.auth.presentation.annotation.AuthMember;
import com.a702.finafanbe.core.funding.funding.application.FundingService;
import com.a702.finafanbe.core.funding.funding.dto.*;
import com.a702.finafanbe.core.user.entity.User;
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
            @RequestBody CreateFundingRequest request,
            @AuthMember User user
    ) {
        
        fundingService.createFunding(request, user);
        return ResponseUtil.success();
    }

    // 펀딩 목록 조회
    @GetMapping
    public ResponseEntity<ResponseData<List<GetFundingResponse>>> getFunding(
            @RequestParam(defaultValue = "all") String filter,
            @AuthMember User user
    ) {
        return ResponseUtil.success(fundingService.getFunding(user, filter));
    }

    // 펀딩 상세 조회
    @GetMapping("/{fundingId}")
    public ResponseEntity<ResponseData<GetFundingDetailResponse>> getFunding(
            @PathVariable Long fundingId,
            @AuthMember User user
    ) {
        return ResponseUtil.success(fundingService.getFundingDetail(fundingId, user));
    }

    // 펀딩 상세 조회 - 입금내역
    @GetMapping("/{fundingId}/transaction")
    public ResponseEntity<ResponseData<List<GetFundingPendingTransactionResponse>>> getFundingPendingTransactions(
            @PathVariable Long fundingId,
            @RequestParam(defaultValue = "all") String filter,
            @AuthMember User user
    ) {
        return ResponseUtil.success(fundingService.getFundingPendingTransaction(user, fundingId, filter));
    }

    // 펀딩 가입
    @GetMapping("/{fundingGroupId}/join")
    public ResponseEntity<?> joinFunding(
            @PathVariable Long fundingGroupId,
            @AuthMember User user
    ) {
        fundingService.joinFunding(user, fundingGroupId);
        return ResponseUtil.success();
    }

    // 펀딩 입금
    @PostMapping("/{fundingGroupId}/deposit")
    public ResponseEntity<?> participateFunding(
            @PathVariable Long fundingGroupId,
            @RequestBody FundingPendingTransactionRequest request,
            @AuthMember User user
    ) {
        
        fundingService.supportFunding(request, user, fundingGroupId);
        return ResponseUtil.success();
    }

    // 펀딩 설명 변경
    @PostMapping("/{fundingId}/update")
    public ResponseEntity<?> updateFundingDescription(
            @RequestBody UpdateFundingDescriptionRequest request,
            @PathVariable Long fundingId,
            @AuthMember User user
    ) {
        fundingService.updateFundingDescription(request, user, fundingId);
        return ResponseUtil.success();
    }

    // 펀딩 탈퇴
    @DeleteMapping("/{fundingId}/resign")
    public ResponseEntity<?> resignFunding(
            @PathVariable Long fundingId,
            @AuthMember User user
    ) {
        
        fundingService.resignFunding(user, fundingId);
        return ResponseUtil.success();
    }

    // 펀딩 입금 취소
    @DeleteMapping("/{fundingId}/withdraw")
    public ResponseEntity<?> withdrawFunding(
            @PathVariable Long fundingId,
            @RequestBody WithdrawTransactionRequest request,
            @AuthMember User user
    ) {
        
        fundingService.withdrawFunding(user, fundingId, request);
        return ResponseUtil.success();
    }

    // 펀딩 중도 해지
    @DeleteMapping("/{fundingId}/cancel")
    public ResponseEntity<?> cancelFunding(
            @PathVariable Long fundingId,
            @RequestBody CancelFundingRequest request,
            @AuthMember User user
    ) {
        
        fundingService.cancelFunding(user, fundingId, request);
        return ResponseUtil.success();
    }

    // 펀딩 종료 해지
    @DeleteMapping("/{fundingId}/terminate")
    public ResponseEntity<?> terminateFunding(
            @PathVariable Long fundingId,
            @AuthMember User user
    ) {
        
        fundingService.terminateFunding(user, fundingId);
        return ResponseUtil.success();
    }


}
