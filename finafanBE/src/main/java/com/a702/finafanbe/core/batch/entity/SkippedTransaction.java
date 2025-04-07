package com.a702.finafanbe.core.batch.entity;

import com.a702.finafanbe.core.batch.dto.TransactionRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "skipped_transactions")
public class SkippedTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private String depositAccountNo;

    private Long transactionBalance;

    private String withdrawalAccountNo;

    private String depositTransactionSummary;

    private String withdrawalTransactionSummary;

    @Builder
    private SkippedTransaction(
            String userEmail,
            String depositAccountNo,
            Long transactionBalance,
            String withdrawalAccountNo,
            String depositTransactionSummary,
            String withdrawalTransactionSummary
    ) {
        this.userEmail = userEmail;
        this.depositAccountNo = depositAccountNo;
        this.transactionBalance = transactionBalance;
        this.withdrawalAccountNo = withdrawalAccountNo;
        this.depositTransactionSummary = depositTransactionSummary;
        this.withdrawalTransactionSummary = withdrawalTransactionSummary;
    }

    public static SkippedTransaction create(TransactionRequest request) {
        return SkippedTransaction.builder()
                .userEmail(request.userEmail())
                .depositAccountNo(request.depositAccountNo())
                .transactionBalance(request.transactionBalance())
                .withdrawalAccountNo(request.withdrawalAccountNo())
                .depositTransactionSummary(request.depositTransactionSummary())
                .withdrawalTransactionSummary(request.withdrawalTransactionSummary())
                .build();
    }
}
