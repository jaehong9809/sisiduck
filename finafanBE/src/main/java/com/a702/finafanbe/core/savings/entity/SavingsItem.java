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

    private Long bankId;

    private String savingsItemName;

    private String accountTypeUniqueNo;

    private String accountTypeName;

    private String accountDescription;

    private BigDecimal interestRate;

    private String interestRateDescription;
}