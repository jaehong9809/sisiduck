package com.a702.finafanbe.core.funding.funding.entity;

import com.a702.finafanbe.core.funding.funding.dto.CreateFundingRequest;
import com.a702.finafanbe.core.funding.group.entity.FundingStatus;
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

    public void updateDescription(String description) {
        this.description = description;
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

    public static FundingGroup create(CreateFundingRequest request, Long accountId, FundingStatus status) {
        return FundingGroup.builder()
                    .accountId(accountId)
                    .entertainerId(request.entertainerId())
                    .name(request.accountNickname())
                    .description(request.description())
                    .goalAmount(request.goalAmount())
                    .fundingExpiryDate(request.fundingExpiryDate())
                    .status(status)
                    .build();
    }
}