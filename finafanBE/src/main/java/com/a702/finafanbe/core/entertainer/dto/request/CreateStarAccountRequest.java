package com.a702.finafanbe.core.entertainer.dto.request;

public record CreateStarAccountRequest(
        String userEmail,
        Long entertainerId,
        String accountName
) {
}
