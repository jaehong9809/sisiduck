package com.a702.finafanbe.core.demanddeposit.dto.request;

public record DepositRequest(
        String email,
        String accountNo,
        Long transactionBalance,
        String transactionSummary
) {
}
