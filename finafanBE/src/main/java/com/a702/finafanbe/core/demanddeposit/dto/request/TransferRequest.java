package com.a702.finafanbe.core.demanddeposit.dto.request;

public record TransferRequest(
        String depositAccountNo,
        String depositTransactionSummary,
        Long transactionBalance,
        String withdrawalAccountNo,
        String withdrawalTransactionSummary
) {
}
