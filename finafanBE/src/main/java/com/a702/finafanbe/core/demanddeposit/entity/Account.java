package com.a702.finafanbe.core.demanddeposit.entity;

import com.a702.finafanbe.global.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "accounts")
@SQLDelete(sql = "UPDATE accounts SET deleted_at = CURRENT_TIMESTAMP WHERE account_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "account_no", nullable = false)
    private String accountNo;

    @Column(name = "amount", nullable = false, precision = 50)
    private BigDecimal amount;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "bank_id", nullable = false)
    private Long bankId;

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

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Version
    private Long version;

    public BigDecimal addAmount(BigDecimal amount) {
        return this.amount.add(amount);
    }

    public static Account of(
        Long userId,
        String accountNo,
        String currency,
        String accountName,
        String accountTypeUniqueNo,
        Long bankId,
        Long accountBalance,
        String accountTypeCode,
        Long dailyTransferLimit,
        Long oneTimeTransferLimit
    ) {
        Account account = new Account(
                userId, accountNo, currency,accountName, accountTypeUniqueNo,
                bankId, accountBalance, accountTypeCode,
                dailyTransferLimit, oneTimeTransferLimit
        );
        account.accountDescription = "기본 계좌 설명";
        account.accountPw = 1234;
        account.status = "ACTIVE";
        account.lastTransactionDate = LocalDateTime.now();

        return account;
    }

    private Account(
            Long userId,
            String accountNo,
            String currency,
            String accountName,
            String accountTypeUniqueNo,
            Long bankId,
            Long accountBalance,
            String accountTypeCode,
            Long dailyTransferLimit,
            Long oneTimeTransferLimit
    ){
        this.userId = userId;
        this.accountNo = accountNo;
        this.currency = currency;
        this.accountName = accountName;
        this.accountTypeUniqueNo = accountTypeUniqueNo;
        this.bankId = bankId;
        this.amount = BigDecimal.valueOf(accountBalance);
        this.accountExpiryDate = LocalDateTime.now().plusYears(5);
        this.accountTypeCode = accountTypeCode;
        this.dailyTransferLimit = BigDecimal.valueOf(dailyTransferLimit);
        this.oneTimeTransferLimit = BigDecimal.valueOf(oneTimeTransferLimit);
    }

    public void updateName(String newName) {
        this.accountName = newName;
    }
}
