package com.a702.finafanbe.core.savings.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;
import java.util.List;

public record InquireSavingProductsResponse(
    BaseResponseHeaderIncludeInstitutionCode Header,
    List<REC> REC
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
