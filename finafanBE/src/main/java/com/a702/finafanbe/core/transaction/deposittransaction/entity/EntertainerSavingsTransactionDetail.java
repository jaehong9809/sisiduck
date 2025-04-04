package com.a702.finafanbe.core.transaction.deposittransaction.entity;

import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "entertainer_savings_transaction_details")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE entertainer_savings_transaction_details SET deleted_at = CURRENT_TIMESTAMP WHERE user_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class EntertainerSavingsTransactionDetail extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "deposit_account_id")
    private Long depositAccountId;

    @Column(name = "withdrawal_account_id", nullable = false)
    private Long withdrawalAccountId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, name = "transaction_balance")
    private BigDecimal transactionBalance;

    @Column(nullable = false, name = "transaction_unique_no")
    private Long transactionUniqueNo;

    @Column(nullable = false, name = "message")
    private String message;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void addAmount(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }

    public static EntertainerSavingsTransactionDetail of(
            Long userId,
            Long depositAccountId,
            Long withdrawalAccountNo,
            BigDecimal amount,
            BigDecimal transactionBalance,
            Long transactionUniqueNo,
            String message,
            String imageUrl
    ){
        return new EntertainerSavingsTransactionDetail(
                userId,
                depositAccountId,
                withdrawalAccountNo,
                amount,
                transactionBalance,
                transactionUniqueNo,
                message,
                imageUrl
        );
    }

    private EntertainerSavingsTransactionDetail(
            Long userId,
            Long depositAccountId,
            Long withdrawalAccountId,
            BigDecimal amount,
            BigDecimal transactionBalance,
            Long transactionUniqueNo,
            String message,
            String imageUrl
    ){
        this.userId = userId;
        this.depositAccountId = depositAccountId;
        this.withdrawalAccountId = withdrawalAccountId;
        this.amount = amount;
        this.transactionBalance = transactionBalance;
        this.transactionUniqueNo = transactionUniqueNo;
        this.message = message;
        this.imageUrl = imageUrl;
    }
}
