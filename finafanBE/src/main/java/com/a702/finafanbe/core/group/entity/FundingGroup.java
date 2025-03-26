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

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "entertainer_id")
    private Long entertainerId;

    @Column(name = "funding_name")
    private String name;

    @Column(name = "funding_description")
    private String description;

    @Column(name = "goal_amount")
    private Long goalAmount;

    @Enumerated(EnumType.STRING)
    private FundingStatus status;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    private void prePersist() {
        this.deletedAt = null;
    }

    @Builder
    private FundingGroup(Long accountId, Long entertainerId, String name, String description, Long goalAmount, FundingStatus status) {
        this.accountId = accountId;
        this.entertainerId = entertainerId;
        this.name = name;
        this.description = description;
        this.goalAmount = goalAmount;
        this.status = status;
    }

    public static FundingGroup create(Long accountId, Long entertainerId, String name, String description, Long goalAmount, FundingStatus status) {
        return FundingGroup.builder()
                    .accountId(accountId)
                    .entertainerId(entertainerId)
                    .name(name)
                    .description(description)
                    .goalAmount(goalAmount)
                    .status(status)
                    .build();
    }
}