package com.a702.finafanbe.core.demanddeposit.dto.request;

public record TransactionWithImageResponse(
        Long transactionUniqueNo,
//        String transactionDate,
//        String transactionTime,
//        String transactionType,
//        String transactionTypeName,
//        String transactionAccountNo,
        Long transactionBalance,
        Long transactionAfterBalance,
//        String transactionSummary,
        String transactionMemo,
        String imageUrl
) {
}
