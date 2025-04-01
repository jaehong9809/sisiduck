package com.a702.finafanbe.core.ranking.dto.response;

import java.util.List;

public record RankingDetailResponse(
        Long entertainerId,
        String entertainerName,
        String profileUrl,
        Double totalAmount,
        List<UserRankingResponse> userRankings
) {
}
