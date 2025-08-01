package com.a702.finafanbe.core.demanddeposit.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;

public record DeleteAccountResponse(
    BaseResponseHeaderIncludeInstitutionCode Header,
    REC REC
) {

    public record REC(
        String status,
        String accountNo,
        String refundAccountNo,
        Long accountBalance
    ){

    }
}
