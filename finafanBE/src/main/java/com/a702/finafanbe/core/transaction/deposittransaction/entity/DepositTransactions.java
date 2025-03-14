package com.a702.finafanbe.core.transaction.deposittransaction.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "deposit_transactions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepositTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(name = "transaction_balance", nullable = false)
    private Long transactionBalance;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "transaction_unique_no", nullable = false)
    private Long transactionUniqueNo;

    @Column(name = "withdrawal_account_no", nullable = false, length = 16)
    private String withdrawalAccountNo;

    @Column(name = "deposit_account_no", nullable = false, length = 16)
    private String depositAccountNo;

    @Column(name = "deposit_transaction_summary", nullable = false)
    private String depositTransactionSummary;

    @Column(name = "withdrawal_transaction_summary", nullable = false)
    private String withdrawalTransactionSummary;

    @Column(name = "transaction_type_name", nullable = false, length = 8)
    private String transactionTypeName;

    @Column(name = "transaction_type", nullable = false, length = 1)
    private String transactionType;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;
}
