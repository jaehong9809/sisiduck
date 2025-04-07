package com.a702.finafanbe.core.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "banks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankId;

    @Column(nullable = false, name = "bank_code")
    private String bankCode;

    @Column(nullable = false, name = "bank_name")
    private String bankName;

}
