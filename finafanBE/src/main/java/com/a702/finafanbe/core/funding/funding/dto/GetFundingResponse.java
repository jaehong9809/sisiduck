package com.a702.finafanbe.core.funding.funding.dto;

import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;

import java.time.LocalDateTime;

public record GetFundingResponse (
        EntertainerResponse entertainer,
        Long fundingId,
        String fundingName,
        String fundingAccountNo,
        FundingStatus status,
        Long currentAmount,
        Long goalAmount,
        LocalDateTime fundingExpiryDate
) {
    public static GetFundingResponse of (
            EntertainerResponse entertainer,
            Long fundingId,
            String fundingName,
            String fundingAccountNo,
            FundingStatus status,
            Long currentAmount,
            Long goalAmount,
            LocalDateTime fundingExpiryDate
    ) {
        return new GetFundingResponse(
                entertainer,
                fundingId,
                fundingName,
                fundingAccountNo,
                status,
                currentAmount,
                goalAmount,
                fundingExpiryDate
        );
    }
}
