package com.a702.finafanbe.core.savings.entity;

import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "savings_type")
public abstract class SavingsAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savingsId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "account_nickname")
    private String accountNickname;

    private Long balance;

    @Column(name = "account_expiry_date")
    private LocalDateTime accountExpiryDate;
}
