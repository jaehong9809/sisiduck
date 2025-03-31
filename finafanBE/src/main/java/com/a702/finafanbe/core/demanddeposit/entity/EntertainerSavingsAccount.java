package com.a702.finafanbe.core.demanddeposit.entity;

import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "entertainer_savings_accounts")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntertainerSavingsAccount extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "entertainer_id")
    private Long entertainerId;

    @Column(nullable = false, name="user_id")
    private Long userId;

    @Column(nullable = false, name ="deposit_account_id")
    private Long depositAccountId;

    @Column(nullable = false, name = "withdrawal_account_id")
    private Long withdrawalAccountId;

    @Column(nullable = false, name = "product_name")
    private String productName;

    @Column(nullable = false, name = "interest_rate")
    private Double interestRate;

    @Column(nullable = false, name = "duration")
    private Long duration;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    public static EntertainerSavingsAccount of(
        Long userId,
        Long entertainerId,
        String productName,
        Long depositAccountId,
        Long withdrawalAccountId,
        Double interestRate,
        Long duration,
        String imageUrl
    ) {
        return new EntertainerSavingsAccount(
            userId,
            entertainerId,
            productName,
            depositAccountId,
            withdrawalAccountId,
            interestRate,
            duration,
            imageUrl
        );
    }

    private EntertainerSavingsAccount(
        Long userId,
        Long entertainerId,
        String productName,
        Long depositAccountId,
        Long withdrawalAccountId,
        Double interestRate,
        Long duration,
        String imageUrl
    ){
        this.userId = userId;
        this.entertainerId = entertainerId;
        this.productName = productName;
        this.depositAccountId = depositAccountId;
        this.withdrawalAccountId = withdrawalAccountId;
        this.interestRate = interestRate;
        this.duration = duration;
        this.imageUrl = imageUrl;
    }
}
