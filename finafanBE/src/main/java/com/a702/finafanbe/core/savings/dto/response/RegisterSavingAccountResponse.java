package com.a702.finafanbe.core.savings.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeader;

public record RegisterSavingAccountResponse(
    BaseResponseHeader Header,
    REC REC
) {
    private record REC(
            String bankCode,
            String bankName,
            String accountNo,
            String accountName,
            String withdrawalBankCode,
            String withdrawalAccountNo,
            String interestRate,
            String subscriptionPeriod,
            Long depositBalance,
            String accountCreateDate,
            String accountExpiryDate
    ){}
}
