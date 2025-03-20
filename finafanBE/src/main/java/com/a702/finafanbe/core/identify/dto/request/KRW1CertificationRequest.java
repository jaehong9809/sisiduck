package com.a702.finafanbe.core.identify.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record KRW1CertificationRequest(
    KRW1CertificationRequestHeader Header,
    String accountNo,
    String authText
){
    @Getter
    @AllArgsConstructor
    public static class KRW1CertificationRequestHeader {
        private String apiName;
        private String transmissionDate;
        private String transmissionTime;
        private String institutionCode;
        private String fintechAppNo;
        private String apiServiceCode;
        private String institutionTransactionUniqueNo;
        private String apiKey;
        private String userKey;
    }
}
