package com.a702.finafanbe.core.savings.dto.apidto;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;
import lombok.Getter;

import java.util.List;

@Getter
public class ApiDepositResponse {
    private BaseResponseHeaderIncludeInstitutionCode header;
    private List<Rec> REC;

    @Getter
    public static class Rec {
        private Long transactionUniqueNo;
        private String accountNo;
        private String transactionDate;
        private String transactionType;
        private String transacionName;
        private String transactionAccountNo;
    }
}
