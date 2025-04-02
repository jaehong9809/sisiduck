package com.a702.finafanbe.core.funding.funding.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record GetFundingPendingTransactionResponse(
        Long id,
        String name,
        Long balance,
        String content,
        LocalDateTime createdAt
) {
    public static GetFundingPendingTransactionResponse of(
            Long id,
            String name,
            Long balance,
            String content,
            LocalDateTime createdAt
    ) {
        return new GetFundingPendingTransactionResponse(
                id,
                name,
                balance,
                content,
                createdAt
        );
    }

    @QueryProjection
    public GetFundingPendingTransactionResponse {}
}
