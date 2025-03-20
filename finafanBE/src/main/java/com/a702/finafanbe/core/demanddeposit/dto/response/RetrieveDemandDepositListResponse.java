package com.a702.finafanbe.core.demanddeposit.dto.response;

import lombok.Getter;

import java.util.List;

public record RetrieveDemandDepositListResponse(
        RetrieveDemandDepositListResponseHeader Header,
        List<REC> REC
) {
    @Getter
    public static class RetrieveDemandDepositListResponseHeader {
        private String responseCode;
        private String responseMessage;
        private String apiName;
        private String transmissionDate;
        private String transmissionTime;
        private String institutionCode;
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
