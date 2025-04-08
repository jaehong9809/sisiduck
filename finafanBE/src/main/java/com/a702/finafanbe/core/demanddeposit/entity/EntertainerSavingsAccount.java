package com.a702.finafanbe.core.demanddeposit.entity;

import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Table(name = "entertainer_savings_accounts")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE entertainer_savings_accounts SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class EntertainerSavingsAccount extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "entertainer_id")
    private Long entertainerId;

    @Column(nullable = false, name="user_id")
    private Long userId;

    @Column(nullable = false, name="bank_id")
    private Long bankId;

    @Column(nullable = false, name="amount")
    private BigDecimal amount;

    @Column(nullable = false, name ="account_no")
    private String accountNo;

    @Column(nullable = false, name = "withdrawal_account_id")
    private Long withdrawalAccountId;

    @Column(nullable = false, name = "]t_name")
    private String productName;

    @Column(nullable = false, name = "interest_rate")
    private Double interestRate;

    @Column(nullable = false, name = "duration")
    private Long duration;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "min_subscription_balance", nullable = false)
    private BigDecimal minSubscriptionBalance;

    @Column(name = "max_subscription_balance", nullable = false)
    private BigDecimal maxSubscriptionBalance;

    public static EntertainerSavingsAccount of(
        Long userId,
        Long entertainerId,
        Long bankId,
        String productName,
        String depositAccountNo,
        Long withdrawalAccountId,
        Double interestRate,
        Long duration,
        String imageUrl
    ) {
        EntertainerSavingsAccount entertainerSavingsAccount =  new EntertainerSavingsAccount(
            userId,
            entertainerId,
            bankId,
            productName,
            depositAccountNo,
            withdrawalAccountId,
            interestRate,
            duration,
            imageUrl
        );
        entertainerSavingsAccount.amount = BigDecimal.ZERO;
        entertainerSavingsAccount.minSubscriptionBalance = new BigDecimal("1000");
        entertainerSavingsAccount.maxSubscriptionBalance = new BigDecimal("10000000");
        return entertainerSavingsAccount;
    }

    private EntertainerSavingsAccount(
        Long userId,
        Long entertainerId,
        Long bankId,
        String productName,
        String depositAccountNo,
        Long withdrawalAccountId,
        Double interestRate,
        Long duration,
        String imageUrl
    ){
        this.userId = userId;
        this.entertainerId = entertainerId;
        this.bankId = bankId;
        this.productName = productName;
        this.accountNo = depositAccountNo;
        this.withdrawalAccountId = withdrawalAccountId;
        this.interestRate = interestRate;
        this.duration = duration;
        this.imageUrl = imageUrl;
    }

    public boolean isPresent() {
        return id != null;
    }
    public long getMaintenanceDays(EntertainerSavingsAccount depositAccount) {
        return ChronoUnit.DAYS.between(depositAccount.getCreatedAt(), LocalDateTime.now());
    }
    public void updateAccountName(String newName){
        this.productName = newName;
    }
}
