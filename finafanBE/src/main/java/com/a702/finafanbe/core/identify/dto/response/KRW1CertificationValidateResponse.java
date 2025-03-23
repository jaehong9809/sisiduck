package com.a702.finafanbe.core.identify.dto.response;

import com.a702.finafanbe.global.common.header.BaseResponseHeaderIncludeInstitutionCode;

public record KRW1CertificationValidateResponse(
    BaseResponseHeaderIncludeInstitutionCode Header,
    REC REC
) {

    private record REC(
        String status,
        Long transactionUniqueNo,
        String accountNo
    ){}
}
