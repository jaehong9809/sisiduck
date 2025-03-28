package com.a702.finafanbe.core.savings.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;

public record InquireSavingAccountResponse(
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
            String interestRate,
            String installmentNumber,
            String totalBalance,
            String accountCreateDate,
            String accountExpiryDate
    ){}
}
