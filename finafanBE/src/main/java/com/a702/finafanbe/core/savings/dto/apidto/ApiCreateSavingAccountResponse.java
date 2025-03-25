package com.a702.finafanbe.core.savings.dto.apidto;

import com.a702.finafanbe.global.common.header.BaseResponseHeaderIncludeInstitutionCode;
import lombok.Getter;

@Getter
public class ApiCreateSavingAccountResponse {

    private BaseResponseHeaderIncludeInstitutionCode Header;
    REC REC;

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
