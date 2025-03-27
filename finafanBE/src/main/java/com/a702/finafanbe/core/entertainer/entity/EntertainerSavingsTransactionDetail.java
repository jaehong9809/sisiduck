package com.a702.finafanbe.core.entertainer.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entertainer_savings_transaction_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntertainerSavingsTransactionDetail {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "account_no")
    private String accountNo;

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
            String accountNo,
            Long transactionBalance,
            Long transactionUniqueNo,
            String message,
            String imageUrl
    ){
        return new EntertainerSavingsTransactionDetail(
                userId,
                accountNo,
                transactionBalance,
                transactionUniqueNo,
                message,
                imageUrl
        );
    }

    private EntertainerSavingsTransactionDetail(
            Long userId,
            String accountNo,
            Long transactionBalance,
            Long transactionUniqueNo,
            String message,
            String imageUrl
    ){
        this.userId = userId;
        this.accountNo = accountNo;
        this.transactionBalance = transactionBalance;
        this.transactionUniqueNo = transactionUniqueNo;
        this.message = message;
        this.imageUrl = imageUrl;
    }
}
