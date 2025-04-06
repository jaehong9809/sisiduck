package com.a702.finafanbe.core.savings.entity;

import com.a702.finafanbe.core.funding.funding.dto.CreateFundingRequest;
import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "savings_accounts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SavingsAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "account_type_unique_no", nullable = false)
    private String accountTypeUniqueNo;

    @Column(name = "account_no", nullable = false)
    private String accountNo;

    @Column(name = "account_nickname")
    private String accountNickname;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "account_expiry_date", nullable = false)
    private LocalDateTime accountExpiryDate;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    private void prePersist() {
        deletedAt = null;
        balance = 0L;
    }

    @Builder
    private SavingsAccount(
            Long userId,
            String accountTypeUniqueNo,
            String accountNo,
            String accountNickname,
            Long balance,
            LocalDateTime accountExpiryDate) {
        this.userId = userId;
        this.accountTypeUniqueNo = accountTypeUniqueNo;
        this.accountNo = accountNo;
        this.accountNickname = accountNickname;
        this.balance = balance;
        this.accountExpiryDate = accountExpiryDate;
    }

    public static SavingsAccount create(
            CreateFundingRequest request,
            Long userId,
            String accountTypeUniqueNo,
            String accountNo
    ) {
        return SavingsAccount.builder()
                .userId(userId)
                .accountTypeUniqueNo(accountTypeUniqueNo)
                .accountNo(accountNo)
                .accountNickname(request.accountNickname())
                .balance(0L)
                .accountExpiryDate(LocalDate.now().plusMonths(6).atStartOfDay())
                .build();
    }
}
