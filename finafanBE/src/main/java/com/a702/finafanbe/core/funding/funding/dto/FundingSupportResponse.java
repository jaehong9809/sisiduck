package com.a702.finafanbe.core.funding.funding.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record FundingSupportResponse (
        String name,
        Long balance,
        String content,
        LocalDateTime createdAt
) {
    public static FundingSupportResponse of(
            String name,
            Long balance,
            String content,
            LocalDateTime createdAt
    ) {
        return new FundingSupportResponse(
                name,
                balance,
                content,
                createdAt
        );
    }

    @QueryProjection
    public FundingSupportResponse {}
}
