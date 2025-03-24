package com.a702.finafanbe.core.identify.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;
import lombok.Getter;

public record KRW1CertificationResponse (
    BaseResponseHeaderIncludeInstitutionCode Header,
    REC REC
) {

    @Getter
    public static class REC {
        private String transactionUniqueNo;
        private String accountNo;
    }
}
