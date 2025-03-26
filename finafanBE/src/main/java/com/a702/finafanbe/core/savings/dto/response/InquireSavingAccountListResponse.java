package com.a702.finafanbe.core.savings.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;

import java.util.List;

public record InquireSavingAccountListResponse(
        BaseResponseHeaderIncludeInstitutionCode Header,
        REC REC
) {
    private record REC(
            String bankCode,
            String bankName,
            String userName,
            String accountNo,
            String accountName,
            String accountDescription,
            String withdrawalBankCode,
            String withdrawalBankName,
            String withdrawalAccountNo,
            String subscriptionPeriod,
            Long depositBalance,
            Double interestRate,
            String installmentNumber,
            Long totalBalance,
            String accountCreateDate,
            String accountExpiryDate
    ){}
}
