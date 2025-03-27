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

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "account_no", nullable = false)
    private String accountNo;

    /*precision : 소수점을 포함한 전체 자리 수*/
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
}
