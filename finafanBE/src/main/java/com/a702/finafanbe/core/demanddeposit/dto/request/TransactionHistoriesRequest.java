package com.a702.finafanbe.core.demanddeposit.dto.request;

public record TransactionHistoriesRequest(
        Long accountId
) {
    public static TransactionHistoriesRequest of(Long accountId) {
        return new TransactionHistoriesRequest(accountId);
    }
}
