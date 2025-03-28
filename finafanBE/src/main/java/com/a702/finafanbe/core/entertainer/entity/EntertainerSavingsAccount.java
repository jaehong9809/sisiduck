package com.a702.finafanbe.core.entertainer.entity;

import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table
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

    @Column(nullable = false, name ="account_id")
    private Long depositAccountId;

    @Column(nullable = false, name = "withdrawal_account_id")
    private Long withdrawalAccountId;

    @Column(nullable = false, name = "product_name")
    private String productName;

    public static EntertainerSavingsAccount of(
            Long userId,
            Long entertainerId,
            String productName,
            Long withdrawalAccountId
    ) {
        return new EntertainerSavingsAccount(
                userId,
                entertainerId,
                productName,
                withdrawalAccountId
        );
    }

    private EntertainerSavingsAccount(
            Long userId,
            Long entertainerId,
            String productName,
            Long withdrawalAccountId
    ){
        this.userId = userId;
        this.entertainerId = entertainerId;
        this.productName = productName;
        this.withdrawalAccountId = withdrawalAccountId;
    }
}
