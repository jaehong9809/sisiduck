package com.a702.finafanbe.core.funding.funding.dto;

import java.time.LocalDateTime;
import java.util.List;

public record GetFundingDetailResponse(
        boolean participated,
        EntertainerResponse entertainer,
        GetFundingAdminResponse adminUser,
        String fundingName,
        Long goalAmount,
        Long currentAmount,
        LocalDateTime fundingExpiryDate,
        List<FundingSupportResponse> fundingApplication
) {
    public static GetFundingDetailResponse of(
            boolean participated,
            EntertainerResponse entertainer,
            GetFundingAdminResponse adminUser,
            String fundingName,
            Long goalAmount,
            Long currentAmount,
            LocalDateTime fundingExpiryDate,
            List<FundingSupportResponse> fundingApplication
    ) {
        return new GetFundingDetailResponse(
                participated,
                entertainer,
                adminUser,
                fundingName,
                goalAmount,
                currentAmount,
                fundingExpiryDate,
                fundingApplication
        );
    }
}
