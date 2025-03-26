package com.a702.finafanbe.core.savings.presentation;

import com.a702.finafanbe.core.auth.presentation.annotation.AuthMember;
import com.a702.finafanbe.core.savings.application.FundingService;
import com.a702.finafanbe.core.savings.dto.CreateFundingRequest;
import com.a702.finafanbe.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/fundings")
@RequiredArgsConstructor
public class FundingController {

    private final FundingService fundingService;

    @PostMapping
    public ResponseEntity<?> createFunding(@RequestBody CreateFundingRequest request, @AuthMember User user) {
        fundingService.createFunding(request, user.getUserId());
        return ResponseEntity.ok().build();
    }
}
