package com.a702.finafanbe.core.entertainer.dto.request;

public record EntertainerTransactionHistoriesRequest(
        String email,
        String accountNo,
        String orderByType
) {
}
