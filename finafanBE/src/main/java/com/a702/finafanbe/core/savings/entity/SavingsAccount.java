package com.a702.finafanbe.core.savings.entity;

import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "savings_accounts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SavingsAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "user_id", nullable = false)
    protected Long userId;

    @Column(name = "savings_item_id", nullable = false)
    protected Long savingsItemId;

    @Column(name = "account_no", nullable = false)
    protected String accountNo;

    @Column(name = "account_nickname")
    protected String accountNickname;

    protected Long balance;

    @Column(name = "account_expiry_date", nullable = false)
    protected LocalDateTime accountExpiryDate;

    @Builder
    protected SavingsAccount(Long userId, Long savingsItemId, String accountNo, String accountNickname, Long balance, LocalDateTime accountExpiryDate) {
        this.userId = userId;
        this.savingsItemId = savingsItemId;
        this.accountNo = accountNo;
        this.accountNickname = accountNickname;
        this.balance = balance;
        this.accountExpiryDate = accountExpiryDate;
    }
}
