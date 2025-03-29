package com.a702.finafanbe.core.entertainer.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InquireEntertainerAccountResponse(
        String bankCode,
        String bankName,
        String accountNo,
        String accountName,
        BigDecimal dailyTransferLimit,
        BigDecimal oneTimeTransferLimit,
        BigDecimal accountBalance,
        LocalDateTime lastTransactionDate,
        String currency,
        String imageUrl
) {
    public static InquireEntertainerAccountResponse of(
            String bankCode,
            String bankName,
            String accountNo,
            String accountName,
            BigDecimal dailyTransferLimit,
            BigDecimal oneTimeTransferLimit,
            BigDecimal accountBalance,
            LocalDateTime lastTransactionDate,
            String currency,
            String imageUrl
    ){
        return new InquireEntertainerAccountResponse(
                bankCode,
                bankName,
                accountNo,
                accountName,
                dailyTransferLimit,
                oneTimeTransferLimit,
                accountBalance,
                lastTransactionDate,
                currency,
                imageUrl
        );
    }
}
