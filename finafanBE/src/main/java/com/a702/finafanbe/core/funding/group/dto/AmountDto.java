package com.a702.finafanbe.core.funding.group.dto;

public record AmountDto(
        String content,
        Long amount
) {
    public static AmountDto of(
            String content,
            Long amount
    ) {
        return new AmountDto(
                content,
                amount
        );
    }
}
