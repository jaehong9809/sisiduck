package com.a702.finafanbe.core.savings.entity;

import com.a702.finafanbe.global.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalSavings extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personalSavingId;

    private Long savingsId;

    private Long userId;

    private Long entertainerId;

    private String accountNickname;

    private String accountNo;

    private Long balance;

    private LocalDateTime lastTransactionDate;

    private LocalDateTime accountExpiryDate;




}
