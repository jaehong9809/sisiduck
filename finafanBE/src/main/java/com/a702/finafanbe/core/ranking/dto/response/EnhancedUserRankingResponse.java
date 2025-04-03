package com.a702.finafanbe.core.ranking.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record EnhancedUserRankingResponse(
        int rank,
        Long userId,
        String userName,
        BigDecimal totalAmount,
        List<UserTransactionDetailResponse> transactions
) {
    public static EnhancedUserRankingResponse from(
            int rank,
            Long userId,
            String userName,
            BigDecimal totalAmount,
            List<UserTransactionDetailResponse> transactions) {
        return new EnhancedUserRankingResponse(
                rank,
                userId,
                userName,
                totalAmount,
                transactions);
    }

    public static EnhancedUserRankingResponse from(
            UserRankingResponse response,
            List<UserTransactionDetailResponse> transactions) {
        return new EnhancedUserRankingResponse(
                response.rank(),
                response.userId(),
                response.userName(),
                BigDecimal.valueOf(response.amount()),
                transactions);
    }
}