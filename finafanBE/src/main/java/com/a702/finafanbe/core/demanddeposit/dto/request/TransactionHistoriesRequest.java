package com.a702.finafanbe.core.demanddeposit.dto.request;

public record TransactionHistoriesRequest(
        String email,
        String accountNo,
        String startDate,
        String endDate,
        String transactionType,
        String orderByType
) {
}
