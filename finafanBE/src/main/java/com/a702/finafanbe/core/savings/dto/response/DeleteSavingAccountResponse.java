package com.a702.finafanbe.core.savings.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;

public record DeleteSavingAccountResponse(
        BaseResponseHeaderIncludeInstitutionCode Header,
        REC REC
) {
    private record REC(
            String status,
            String bankCode,
            String bankName,
            String accountNo,
            String accountName,
            Long totalBalance,
            Double earlyTerminationInterest,
            Long earlyTerminationBalance,
            Long earlyTerminationDate
    ){}
}
