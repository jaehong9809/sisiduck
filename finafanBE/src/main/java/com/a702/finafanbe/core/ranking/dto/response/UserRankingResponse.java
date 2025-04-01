package com.a702.finafanbe.core.ranking.dto.response;

public record UserRankingResponse(
        int rank,
        Long userId,
        String userName,
        Double amount
) {
}
