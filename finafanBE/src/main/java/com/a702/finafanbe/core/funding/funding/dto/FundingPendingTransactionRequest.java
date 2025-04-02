package com.a702.finafanbe.core.funding.funding.dto;

public record FundingPendingTransactionRequest(
        Long accountId,
        Long balance,
        String content
) {
    public static FundingPendingTransactionRequest of (
            Long accountId,
            Long balance,
            String content
    ) {
        return new FundingPendingTransactionRequest(
                accountId,
                balance,
                content
        );
    }
}
