package com.a702.finafanbe.core.entertainer.dto.request;

public record CreateStarAccountRequest(
        Long entertainerId,
        String productName,
        Long withdrawalAccountId
) {
}
