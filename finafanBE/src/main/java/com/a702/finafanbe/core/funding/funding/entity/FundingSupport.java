package com.a702.finafanbe.core.funding.funding.entity;

import com.a702.finafanbe.core.funding.funding.dto.FundingSupportRequest;
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
@SQLDelete(sql = "UPDATE funding_supports SET deleted_at = NOW() WHERE id = ?")
@Table(name = "funding_supports")
public class FundingSupport extends BaseEntity {

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
    private FundingSupport(Long userId, Long fundingGroupId, Long accountId, Long balance, String content, String depositUserName) {
        this.userId = userId;
        this.fundingGroupId = fundingGroupId;
        this.accountId = accountId;
        this.balance = balance;
        this.content = content;
        this.depositUserName = depositUserName;
    }

    public static FundingSupport create(FundingSupportRequest request, Long userId, Long fundingGroupId, String depositUserName) {
        return FundingSupport.builder()
                .userId(userId)
                .fundingGroupId(fundingGroupId)
                .accountId(request.accountId())
                .balance(request.balance())
                .content(request.content())
                .depositUserName(depositUserName)
                .build();
    }
}
