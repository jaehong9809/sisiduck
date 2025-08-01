package com.a702.finafanbe.core.identify.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;

public record KRW1CertificationValidateResponse(
    BaseResponseHeaderIncludeInstitutionCode Header,
    REC REC
) {

    public record REC(
        String status,
        Long transactionUniqueNo,
        String accountNo
    ){}
}
