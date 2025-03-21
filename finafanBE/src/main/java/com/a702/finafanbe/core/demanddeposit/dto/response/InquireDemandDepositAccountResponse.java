package com.a702.finafanbe.core.demanddeposit.dto.response;

import com.a702.finafanbe.global.common.header.InstitutionResponseHeader;
import lombok.Getter;

public record InquireDemandDepositAccountResponse(
    InstitutionResponseHeader Header,
    REC REC
) {
    @Getter
    public static class REC {
        private String accountTypeUniqueNo;
        private String bankCode;
        private String bankName;
        private String accountTypeCode;
        private String accountTypeName;
        private String accountName;
        private String accountDescription;
        private String accountType;
    }
}
