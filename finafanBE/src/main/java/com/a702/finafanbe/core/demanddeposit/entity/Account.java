package com.a702.finafanbe.core.demanddeposit.entity;

import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "accounts")
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "account_no", nullable = false)
    private String accountNo;

    @Column(name = "balance", nullable = false, precision = 50)
    private BigDecimal balance;

    @Column(name = "interest_rate", nullable = false)
    private BigDecimal interestRate;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "bank_code", nullable = false, length = 3)
    private String bankCode;

    @Column(name = "account_pw", nullable = false)
    private int accountPw;

    @Column(name = "account_type_unique_no", nullable = false, length = 20)
    private String accountTypeUniqueNo;

    @Column(name = "account_type_code", nullable = false, length = 3)
    private String accountTypeCode;

    @Column(name = "account_name", nullable = false, length = 20)
    private String accountName;

    @Column(name = "account_description", nullable = false)
    private String accountDescription;

    @Column(name = "currency", nullable = false, length = 8)
    private String currency;

    @Column(name = "daily_transfer_limit", nullable = false)
    private BigDecimal dailyTransferLimit;

    @Column(name = "one_time_transfer_limit", nullable = false)
    private BigDecimal oneTimeTransferLimit;

    @Column(name = "last_transaction_date", nullable = false)
    private LocalDateTime lastTransactionDate;

    @Column(name = "account_expiry_date", nullable = false, length = 8)
    private LocalDateTime accountExpiryDate;

    @Column(name = "rate_description")
    private String rateDescription;

    @Column(name = "min_subscription_balance", nullable = false)
    private BigDecimal minSubscriptionBalance;

    @Column(name = "max_subscription_balance", nullable = false)
    private BigDecimal maxSubscriptionBalance;

    @Column(name = "subscription_period")
    private LocalDateTime subscriptionPeriod;

    public static Account of(
        Long userId,
        String accountNo,
        String bankCode,
        String currency
    ) {
        Account account = new Account(userId, accountNo, bankCode, currency);
        account.accountDescription = "기본 계좌 설명";
        account.accountExpiryDate = LocalDateTime.now().plusYears(5);
        account.accountName = "기본 계좌";
        account.accountPw = 1234;
        account.accountTypeCode = "1";
        account.accountTypeUniqueNo = "SAVE001";
        account.balance = BigDecimal.ZERO;
        account.interestRate = new BigDecimal("0.01");
        account.status = "ACTIVE";
        account.dailyTransferLimit = new BigDecimal("5000000");
        account.oneTimeTransferLimit = new BigDecimal("1000000");
        account.lastTransactionDate = LocalDateTime.now();
        account.minSubscriptionBalance = new BigDecimal("1000");
        account.maxSubscriptionBalance = new BigDecimal("10000000");

        return account;
    }

    private Account(
            Long userId,
            String accountNo,
            String bankCode,
            String currency
    ){
        this.userId = userId;
        this.accountNo = accountNo;
        this.bankCode = bankCode;
        this.currency = currency;
    }
}
