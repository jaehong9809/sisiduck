package com.a702.finafanbe.core.transaction.deposittransaction.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entertainer_savings_transaction_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EntertainerSavingsTransactionDetail {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "deposit_account_no")
    private String depositAccountNo;

    @Column(name = "withdrawal_account_no", nullable = false)
    private String withdrawalAccountNo;

    @Column(nullable = false, name = "transaction_balance")
    private Long transactionBalance;

    @Column(nullable = false, name = "transaction_unique_no")
    private Long transactionUniqueNo;

    @Column(nullable = false, name = "message")
    private String message;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    public static EntertainerSavingsTransactionDetail of(
            Long userId,
            String depositAccountNo,
            String withdrawalAccountNo,
            Long transactionBalance,
            Long transactionUniqueNo,
            String message,
            String imageUrl
    ){
        return new EntertainerSavingsTransactionDetail(
                userId,
                depositAccountNo,
                withdrawalAccountNo,
                transactionBalance,
                transactionUniqueNo,
                message,
                imageUrl
        );
    }

    private EntertainerSavingsTransactionDetail(
            Long userId,
            String depositAccountNo,
            String withdrawalAccountNo,
            Long transactionBalance,
            Long transactionUniqueNo,
            String message,
            String imageUrl
    ){
        this.userId = userId;
        this.depositAccountNo = depositAccountNo;
        this.withdrawalAccountNo = withdrawalAccountNo;
        this.transactionBalance = transactionBalance;
        this.transactionUniqueNo = transactionUniqueNo;
        this.message = message;
        this.imageUrl = imageUrl;
    }
}
