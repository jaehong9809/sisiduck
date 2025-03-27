package com.a702.finafanbe.core.group.entity;

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
@Table(name = "funding_groups")
@SQLDelete(sql = "UPDATE funding_groups SET deleted_at = NOW() WHERE id = ?")
public class FundingGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "entertainer_id", nullable = false)
    private Long entertainerId;

    @Column(name = "funding_name", nullable = false)
    private String name;

    @Column(name = "funding_description", nullable = false)
    private String description;

    @Column(name = "goal_amount", nullable = false)
    private Long goalAmount;

    @Column(name = "funding_expiry_date", nullable = false)
    private LocalDateTime fundingExpiryDate;

    @Enumerated(EnumType.STRING)
    private FundingStatus status;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    private void prePersist() {
        this.deletedAt = null;
    }

    @Builder
    private FundingGroup(Long accountId, Long entertainerId, String name, String description, Long goalAmount, LocalDateTime fundingExpiryDate, FundingStatus status) {
        this.accountId = accountId;
        this.entertainerId = entertainerId;
        this.name = name;
        this.description = description;
        this.goalAmount = goalAmount;
        this.fundingExpiryDate = fundingExpiryDate;
        this.status = status;
    }

    public static FundingGroup create(Long accountId, Long entertainerId, String name, String description, Long goalAmount, LocalDateTime fundingExpiryDate, FundingStatus status) {
        return FundingGroup.builder()
                    .accountId(accountId)
                    .entertainerId(entertainerId)
                    .name(name)
                    .description(description)
                    .goalAmount(goalAmount)
                    .fundingExpiryDate(fundingExpiryDate)
                    .status(status)
                    .build();
    }
}