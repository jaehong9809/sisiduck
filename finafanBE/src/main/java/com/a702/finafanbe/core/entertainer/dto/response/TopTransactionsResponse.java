package com.a702.finafanbe.core.entertainer.dto.response;

import com.a702.finafanbe.core.entertainer.entity.Entertainer;

import java.util.List;

public record TopTransactionsResponse(
    Long entertainerId,
    String entertainerName,
    String entertainerProfileUrl,
    List<TopTransactionResponse> transactions
) {
    public static TopTransactionsResponse of(
        Entertainer entertainer,
        List<TopTransactionResponse> transactions) {
        return new TopTransactionsResponse(
            entertainer.getEntertainerId(),
            entertainer.getEntertainerName(),
            entertainer.getEntertainerProfileUrl(),
            transactions
        );
    }
}