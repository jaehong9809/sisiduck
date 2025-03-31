package com.a702.finafanbe.core.entertainer.dto.response;

import java.math.BigDecimal;

public record AccountInfo(
        Long accountId,
        String accountNo,
        String accountName,
        BigDecimal balance,
        String bankName,
        String bankCode
) {
}