package com.a702.finafanbe.core.entertainer.dto.request;

public record StarTransferRequest(
        Long depositAccountId,
        Long transactionBalance,
        String message
) {
}
