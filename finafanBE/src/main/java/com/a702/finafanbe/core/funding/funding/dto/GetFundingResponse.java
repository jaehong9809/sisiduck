package com.a702.finafanbe.core.funding.funding.dto;

import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public record GetFundingResponse (
        EntertainerResponse entertainer,
        Long fundingId,
        String fundingName,
        String fundingAccountNo,
        FundingStatus status,
        Long currentAmount,
        Long goalAmount,
        LocalDate fundingExpiryDate
) {
    public static GetFundingResponse of (
            EntertainerResponse entertainer,
            Long fundingId,
            String fundingName,
            String fundingAccountNo,
            FundingStatus status,
            Long currentAmount,
            Long goalAmount,
            LocalDate fundingExpiryDate
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

    @QueryProjection
    public GetFundingResponse {}
}
