package com.a702.finafanbe.core.savings.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;

public record RegisterSavingProductResponse(
        BaseResponseHeaderIncludeInstitutionCode Header,
        REC REC
) {

    private record REC(
        String accountTypeUniqueNo,
        String bankCode,
        String bankName,
        String accountTypeCode,
        String accountTypeName,
        String accountName,
        String accountDescription,
        String subscriptionPeriod,
        Long minSubscriptionBalance,
        Long maxSubscriptionBalance,
        Double interestRate,
        String rateDescription
    ){}

}
