package com.a702.finafanbe.core.entertainer.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TopTransactionResponse(
    Long transactionId,
    Long userId,
    String userName,
    BigDecimal amount,
    LocalDateTime transactionTime,
    String message,
    String imageUrl
) {
    public static TopTransactionResponse of(
        Long transactionId,
        Long userId,
        String userName,
        BigDecimal amount,
        LocalDateTime transactionTime,
        String message,
        String imageUrl) {
        return new TopTransactionResponse(
            transactionId,
            userId,
            userName,
            amount,
            transactionTime,
            message,
            imageUrl
        );
    }
}