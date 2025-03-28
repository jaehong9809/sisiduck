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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "funding_applications")
public class FundingApplication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "funding_group_id", nullable = false)
    private Long fundingGroupId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "content")
    private String content;

    @Column(name = "deposit_user_name", nullable = false)
    private String depositUserName;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    private void prePersist() {
        deletedAt = null;
    }

    @Builder
    private FundingApplication(Long userId, Long fundingGroupId, Long accountId, Long balance, String content, String depositUserName) {
        this.userId = userId;
        this.fundingGroupId = fundingGroupId;
        this.accountId = accountId;
        this.balance = balance;
        this.content = content;
        this.depositUserName = depositUserName;
    }

    public static FundingApplication create(Long userId, Long fundingGroupId, Long accountId, Long balance, String content, String depositUserName) {
        return FundingApplication.builder()
                .userId(userId)
                .fundingGroupId(fundingGroupId)
                .accountId(accountId)
                .balance(balance)
                .content(content)
                .depositUserName(depositUserName)
                .build();
    }
}
