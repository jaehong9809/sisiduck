package com.a702.finafanbe.core.identify.dto.response;

import lombok.Getter;

public record KRW1CertificationResponse (
    RetrieveDemandDepositResponseHeader Header,
    REC REC
) {
    @Getter
    public static class RetrieveDemandDepositResponseHeader {
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
        private String transactionUniqueNo;
        private String accountNo;
    }
}
