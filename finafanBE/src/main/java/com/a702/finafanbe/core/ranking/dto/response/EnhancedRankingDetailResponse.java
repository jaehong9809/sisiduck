package com.a702.finafanbe.core.ranking.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record EnhancedRankingDetailResponse(
        Long entertainerId,
        String entertainerName,
        String profileUrl,
        BigDecimal totalAmount,
        List<EnhancedUserRankingResponse> userRankings
) {
    public static EnhancedRankingDetailResponse from(
            RankingDetailResponse original,
            List<EnhancedUserRankingResponse> enhancedUserRankings) {
        return new EnhancedRankingDetailResponse(
                original.entertainerId(),
                original.entertainerName(),
                original.profileUrl(),
                BigDecimal.valueOf(original.totalAmount()),
                enhancedUserRankings);
    }
}