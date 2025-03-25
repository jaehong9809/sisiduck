package com.a702.finafanbe.core.demanddeposit.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;
import lombok.Getter;

public record UpdateDemandDepositAccountDepositResponse(
    BaseResponseHeaderIncludeInstitutionCode Header,
    REC REC
) {
    @Getter
    public static class REC{
        private Long transactionUniqueNo;
        private String transactionDate;
    }
}
