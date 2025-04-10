package com.a702.finafanbe.core.batch.dto;


public record TransactionRequest (
        Long id,
        // 출금되는 유저의 email
        String userEmail,
        // 입금 계좌 번호
        String depositAccountNo,
        // 거래 금액
        Long transactionBalance,
        // 출금될 계좌 번호
        String withdrawalAccountNo,
        // 입금계좌 거래 요약 내용
        String depositTransactionSummary,
        // 출금 계좌 요약 내용
        String withdrawalTransactionSummary
) {

    public static TransactionRequest of (
            Long id,
            String userEmail,
            String depositAccountNo,
            Long transactionBalance,
            String withdrawalAccountNo,
            String depositTransactionSummary,
            String withdrawalTransactionSummary
    ) {
        return new TransactionRequest(
                id,
                userEmail,
                depositAccountNo,
                transactionBalance,
                withdrawalAccountNo,
                depositTransactionSummary,
                withdrawalTransactionSummary
        );
    }
}
