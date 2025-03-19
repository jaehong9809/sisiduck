package com.a702.finafanbe.core.savings.entity;

import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.math.BigDecimal;

@MappedSuperclass
public class Savings extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savingsId;

    private String bankCode;

    private String bankName;

    private String accountName;

    private String accountDiscription;

    private String accountTypeUniqueNo;

    private String accountTypeCode;

    private BigDecimal interestRate;

    private String interestRateDiscription;
}
