package com.a702.finafanbe.core.demanddeposit.dto.response;

import com.a702.finafanbe.global.common.header.BaseResponseHeaderIncludeInstitutionCode;
import lombok.Getter;

public record CreateAccountResponse (
    BaseResponseHeaderIncludeInstitutionCode Header,
    REC REC
) {
    @Getter
    public static class REC {
        private String bankCode;
        private String accountNo;
        private Currency currency;
    }

    @Getter
    public static class Currency{
        private String currency;
        private String currencyName;
    }
}
