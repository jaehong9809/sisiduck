package com.a702.finafanbe.core.entertainer.dto.response;

import java.math.BigDecimal;

public record EntertainerDepositResponse(
        Long depositAccountId,
        Long withdrawalAccountId,
        BigDecimal transactionBalance,
        Long transactionUniqueNo,
        String message,
        String imageUrl
) {
    public static EntertainerDepositResponse of(
            Long depositAccountId,
            Long withdrawalAccountId,
            BigDecimal transactionBalance,
            Long transactionUniqueNo,
            String message,
            String imageUrl
    ){
        return new EntertainerDepositResponse(
                depositAccountId,
                withdrawalAccountId,
                transactionBalance,
                transactionUniqueNo,
                message,
                imageUrl);
    }
}
