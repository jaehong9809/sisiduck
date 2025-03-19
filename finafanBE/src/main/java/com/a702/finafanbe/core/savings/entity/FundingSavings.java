package com.a702.finafanbe.core.savings.entity;

import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingSavings extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupSavingsId;

    @Column(name = "savings_item_id")
    private Long savingsItemId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "entertainer_id")
    private Long entertainerId;

    @Column(name = "account_nickname")
    private String accountNickname;

    @Column(length = 255)
    private String description;

    @Column(name = "account_no")
    private String accountNo;

    private Long balance;

    @Column(name = "account_expiry_date")
    private LocalDateTime accountExpiryDate;
}
