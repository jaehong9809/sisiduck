package com.a702.finafanbe.core.demanddeposit.dto.request;

public record TransactionWithImageResponse(
    Long transactionId,
    Long transactionUniqueNo,
    Long transactionAfterBalance,
    Long transactionBalance,
    String transactionMemo,
    String imageUrl
) {
}
