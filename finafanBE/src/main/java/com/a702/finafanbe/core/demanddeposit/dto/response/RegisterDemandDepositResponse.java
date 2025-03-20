package com.a702.finafanbe.core.demanddeposit.dto.response;

import lombok.Getter;

public record RegisterDemandDepositResponse(
        CreateDemandDepositResponseHeader Header,
        REC REC
) {
    @Getter
    public static class CreateDemandDepositResponseHeader {
        private String responseCode;
        private String responseMessage;
        private String apiName;
        private String transmissionDate;
        private String transmissionTime;
        private String apiKey;
        private String apiServiceCode;
        private String institutionTransactionUniqueNo;
    }

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
