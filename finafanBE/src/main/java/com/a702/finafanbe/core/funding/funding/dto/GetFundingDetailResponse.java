package com.a702.finafanbe.core.funding.funding.dto;

import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;

import java.time.LocalDate;

public record GetFundingDetailResponse(
        boolean participated,
        EntertainerResponse entertainer,
        GetFundingAdminResponse adminUser,
        String fundingName,
        String description,
        FundingStatus status,
        Long goalAmount,
        Long currentAmount,
        LocalDate fundingExpiryDate
) {
    public static GetFundingDetailResponse of(
            boolean participated,
            EntertainerResponse entertainer,
            GetFundingAdminResponse adminUser,
            String fundingName,
            String description,
            FundingStatus status,
            Long goalAmount,
            Long currentAmount,
            LocalDate fundingExpiryDate
    ) {
        return new GetFundingDetailResponse(
                participated,
                entertainer,
                adminUser,
                fundingName,
                description,
                status,
                goalAmount,
                currentAmount,
                fundingExpiryDate
        );
    }
}
