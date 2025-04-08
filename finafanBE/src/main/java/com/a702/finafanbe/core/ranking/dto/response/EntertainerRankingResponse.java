package com.a702.finafanbe.core.ranking.dto.response;

public record EntertainerRankingResponse(
        int rank,
        Long entertainerId,
        String entertainerName,
        String profileUrl,
        Double totalAmount
) {
}
