package com.a702.finafanbe.core.batch.dto;

import com.a702.finafanbe.core.funding.funding.entity.FundingTransactionStatus;

public record TransactionResponse(
        Long id,
        FundingTransactionStatus status
) {
    public static TransactionResponse of(
            Long id,
            FundingTransactionStatus status
    ) {
        return new TransactionResponse(
                id,
                status
        );
    }
}
