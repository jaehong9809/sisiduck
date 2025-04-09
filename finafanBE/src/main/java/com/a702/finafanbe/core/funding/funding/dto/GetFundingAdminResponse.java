package com.a702.finafanbe.core.funding.funding.dto;

public record GetFundingAdminResponse (
        Long id,
        String adminName,
        int fundingCount,
        int fundingSuccessCount
) {
    public static GetFundingAdminResponse of(
            Long id,
            String adminName,
            int fundingCount,
            int fundingSuccessCount
    ) {
        return new GetFundingAdminResponse(
            id,
            adminName,
            fundingCount,
            fundingSuccessCount
            );
    }
}
