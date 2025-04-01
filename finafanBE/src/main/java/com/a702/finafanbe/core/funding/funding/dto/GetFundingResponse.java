package com.a702.finafanbe.core.funding.funding.dto;

import java.time.LocalDateTime;

public record GetFundingResponse (
        EntertainerResponse entertainer,
        Long fundingId,
        String fundingName,
        Long currentAmount,
        Long goalAmount,
        LocalDateTime fundingExpiryDate
) {
    public static GetFundingResponse of (
            EntertainerResponse entertainer,
            Long fundingId,
            String fundingName,
            Long currentAmount,
            Long goalAmount,
            LocalDateTime fundingExpiryDate
    ) {
        return new GetFundingResponse(
                entertainer,
                fundingId,
                fundingName,
                currentAmount,
                goalAmount,
                fundingExpiryDate
        );
    }
}
