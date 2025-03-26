package com.a702.finafanbe.core.savings.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;

import java.util.List;

public record InquireSavingExpiryInterestResponse(
        BaseResponseHeaderIncludeInstitutionCode Header,
        REC REC
) {
    private record REC(
            String bankCode,
            String bankName,
            String accountNo,
            String accountName,
            Double interestRate,
            String accountCreateDate,
            String accountExpiryDate,
            Long expiryBalance,
            Long expiryInterest,
            Long expiryTotalBalance
    ){}
}
