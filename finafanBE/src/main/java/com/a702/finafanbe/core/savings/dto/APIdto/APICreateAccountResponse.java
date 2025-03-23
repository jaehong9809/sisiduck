package com.a702.finafanbe.core.savings.dto.APIdto;

import com.a702.finafanbe.core.demanddeposit.dto.response.CreateAccountResponse;
import com.a702.finafanbe.global.common.header.InstitutionResponseHeader;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public record APICreateAccountResponse(InstitutionResponseHeader header, REC rec) {

    @Getter
    public static class REC {
        private String bankCode;
        private String accountNo;
        private Currency currencyName;
    }

    @Getter
    public static class Currency {
        private String currency;
        private String currencyName;
    }
}
