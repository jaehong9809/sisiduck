package com.a702.finafanbe.core.ranking.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UserTransactionDetailResponse(
        Long transactionId,
        Long transactionUniqueNo,
        LocalDateTime transactionTime,
        String message,
        BigDecimal amount,
        String imageUrl
) {
    public static UserTransactionDetailResponse from(
            Long transactionId,
            Long transactionUniqueNo,
            LocalDateTime transactionTime,
            String message,
            BigDecimal amount,
            String imageUrl) {
        return new UserTransactionDetailResponse(
                transactionId,
                transactionUniqueNo,
                transactionTime,
                message,
                amount,
                imageUrl);
    }
}