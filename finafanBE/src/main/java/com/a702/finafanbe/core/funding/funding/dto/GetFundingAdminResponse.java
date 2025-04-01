package com.a702.finafanbe.core.funding.funding.dto;

public record GetFundingAdminResponse (
        String adminName,
        int fundingCount,
        int fundingSuccessCount
) {
    public static GetFundingAdminResponse of(
            String adminName,
            int fundingCount,
            int fundingSuccessCount
    ) {
        return new GetFundingAdminResponse(
            adminName,
            fundingCount,
            fundingSuccessCount
            );
    }
}
