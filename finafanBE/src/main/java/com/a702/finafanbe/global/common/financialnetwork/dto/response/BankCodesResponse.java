package com.a702.finafanbe.global.common.financialnetwork.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;
import java.util.List;

public record BankCodesResponse(
    BaseResponseHeaderIncludeInstitutionCode Header,
    List<REC> REC
) {

    private record REC(
        String bankCode,
        String bankName
    ){}
}
