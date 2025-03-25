package com.a702.finafanbe.core.demanddeposit.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;

public record InquireAccountBalanceResponse(
    BaseResponseHeaderIncludeInstitutionCode Header,
    REC REC
) {

    private record REC(
        String bankCode,
        String accountNo,
        Long accountBalance,
        String accountCreatedDate,
        String accountExpiryDate,
        String lastTransactionDate,
        String currency
    ){

    }
}
