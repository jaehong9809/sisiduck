package com.a702.finafanbe.core.demanddeposit.dto.response;

import com.a702.finafanbe.global.common.header.BaseResponseHeaderIncludeInstitutionCode;

public record DeleteAccountResponse(
    BaseResponseHeaderIncludeInstitutionCode Header,
    REC REC
) {

    private record REC(
        String status,
        String accountNo,
        String refundAccountNo,
        Long accountBalance
    ){

    }
}
