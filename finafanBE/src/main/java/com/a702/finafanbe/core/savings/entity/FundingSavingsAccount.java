package com.a702.finafanbe.core.savings.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingSavingsAccount extends SavingsAccount {

    @Column(name = "entertainer_id")
    private Long entertainerId;

    @Column(length = 255)
    private String description;

    @Column(name = "funding_expiry_date")
    private LocalDateTime fundingExpiryDate;

    @Column(name = "funding_status")
    private String fundingStatus;

    @Column(name = "funding_goal_amount")
    private Long fundingGoalAmount;
}
