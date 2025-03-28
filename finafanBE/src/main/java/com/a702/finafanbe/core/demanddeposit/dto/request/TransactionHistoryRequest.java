package com.a702.finafanbe.core.demanddeposit.dto.request;

public record TransactionHistoryRequest(
        String email,
        String accountNo,
        Long transactionUniqueNo
) {
}
