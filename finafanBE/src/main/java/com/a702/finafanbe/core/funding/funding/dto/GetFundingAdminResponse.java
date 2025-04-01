package com.a702.finafanbe.core.funding.funding.dto;

public record GetFundingAdminResponse (
        String adminName,
        String description,
        int fundingCount,
        int fundingSuccessCount
) {
    public static GetFundingAdminResponse of(
            String adminName,
            String description,
            int fundingCount,
            int fundingSuccessCount
    ) {
        return new GetFundingAdminResponse(
            adminName,
            description,
            fundingCount,
            fundingSuccessCount
            );
    }
}
