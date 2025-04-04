package com.a702.finafanbe.core.entertainer.dto.response;

import com.a702.finafanbe.core.entertainer.entity.Entertainer;

import java.util.List;

public record TopTransactionsResponse(
    int rank,
    Long entertainerId,
    String entertainerName,
    String entertainerProfileUrl,
    Double totalAmount,
    List<TopTransactionResponse> transactions
) {
    public static TopTransactionsResponse of(
        int rank,
        Entertainer entertainer,
        Double totalAmount,
        List<TopTransactionResponse> transactions
    ) {
        return new TopTransactionsResponse(
            rank,
            entertainer.getEntertainerId(),
            entertainer.getEntertainerName(),
            entertainer.getEntertainerProfileUrl(),
            totalAmount,
            transactions
        );
    }
}