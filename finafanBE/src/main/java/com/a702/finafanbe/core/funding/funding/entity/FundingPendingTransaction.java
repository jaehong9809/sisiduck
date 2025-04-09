package com.a702.finafanbe.core.funding.funding.entity;

import com.a702.finafanbe.core.funding.funding.dto.FundingPendingTransactionRequest;
import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE funding_pending_transactions SET deleted_at = NOW() WHERE id = ?")
@Table(name = "funding_pending_transactions")
public class FundingPendingTransaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "funding_id", nullable = false)
    private Long fundingId;

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

    @Enumerated(EnumType.STRING)
    private FundingTransactionStatus status;

    @PrePersist
    private void prePersist() {
        this.deletedAt = null;
        this.status = FundingTransactionStatus.PENDING;
    }

    public void updateStatus(FundingTransactionStatus status) {
        this.status = status;
    }

    @Builder
    private FundingPendingTransaction(Long userId, Long fundingId, Long accountId, Long balance, String content, String depositUserName) {
        this.userId = userId;
        this.fundingId = fundingId;
        this.accountId = accountId;
        this.balance = balance;
        this.content = content;
        this.depositUserName = depositUserName;
    }

    public static FundingPendingTransaction create(FundingPendingTransactionRequest request, Long userId, Long fundingId, String depositUserName) {
        return FundingPendingTransaction.builder()
                .userId(userId)
                .fundingId(fundingId)
                .accountId(request.accountId())
                .balance(request.balance())
                .content(request.content())
                .depositUserName(depositUserName)
                .build();
    }
}
