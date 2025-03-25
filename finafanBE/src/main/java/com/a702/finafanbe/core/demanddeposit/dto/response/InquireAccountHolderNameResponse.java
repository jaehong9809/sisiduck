package com.a702.finafanbe.core.demanddeposit.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;

public record InquireAccountHolderNameResponse(
    BaseResponseHeaderIncludeInstitutionCode Header,
    REC REC
) {

    private record REC(
        String bankCode,
        String bankName,
        String accountNo,
        String userName,
        String currency
    ){

    }
}
