package com.a702.finafanbe.core.funding.funding.dto;

public record FundingSupportRequest(
        Long accountId,
        Long balance,
        String content
) {
    public static FundingSupportRequest of (
            Long accountId,
            Long balance,
            String content
    ) {
        return new FundingSupportRequest(
                accountId,
                balance,
                content
        );
    }
}
