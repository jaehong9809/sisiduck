package com.a702.finafanbe.core.savings.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "savings_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SavingsItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savingsItemId;

    @Column(name = "bank_id", nullable = false)
    private Long bankId;

    @Column(nullable = false)
    private String savingsItemName;

    @Column(nullable = false)
    private String accountTypeUniqueNo;

    @Column(nullable = false)
    private String accountTypeName;

    @Column(nullable = false)
    private String accountDescription;

    @Column(nullable = false)
    private BigDecimal interestRate;

    private String interestRateDescription;
}