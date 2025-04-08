package com.a702.finafanbe.core.demanddeposit.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record TransactionWithImageResponse(
    Long transactionId,
    Long transactionUniqueNo,
    Long transactionAfterBalance,
    Long transactionBalance,
    String transactionMemo,
    String imageUrl,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime transactionTime
) {
}
