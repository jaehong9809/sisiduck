package com.a702.finafanbe.core.entertainer.dto.response;

import com.a702.finafanbe.core.bank.entity.Bank;

import java.math.BigDecimal;

public record AccountInfo(
        Long accountId,
        String accountNo,
        String accountName,
        BigDecimal balance,
        Bank bank
) {
}