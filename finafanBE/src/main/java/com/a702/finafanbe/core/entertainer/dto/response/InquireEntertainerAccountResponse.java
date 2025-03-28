package com.a702.finafanbe.core.entertainer.dto.response;

public record InquireEntertainerAccountResponse(
        String bankCode,
        String bankName,
        String accountNo,
        String accountName,
        Long dailyTransferLimit,
        Long oneTimeTransferLimit,
        Long accountBalance,
        String lastTransactionDate,
        String currency,
        String imageUrl
) {
    public static InquireEntertainerAccountResponse of(
            String bankCode,
            String bankName,
            String accountNo,
            String accountName,
            Long dailyTransferLimit,
            Long oneTimeTransferLimit,
            Long accountBalance,
            String lastTransactionDate,
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
