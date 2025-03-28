package com.a702.finafanbe.core.entertainer.dto.response;

public record EntertainerDepositResponse(
        String depositAccountNo,
        String withdrawalAccountNo,
        Long transactionBalance,
        Long transactionUniqueNo,
        String message,
        String imageUrl
) {
    public static EntertainerDepositResponse of(
            String depositAccountNo,
            String withdrawalAccountNo,
            Long transactionBalance,
            Long transactionUniqueNo,
            String message,
            String imageUrl
    ){
        return new EntertainerDepositResponse(
                depositAccountNo,
                withdrawalAccountNo,
                transactionBalance,
                transactionUniqueNo,
                message,
                imageUrl);
    }
}
