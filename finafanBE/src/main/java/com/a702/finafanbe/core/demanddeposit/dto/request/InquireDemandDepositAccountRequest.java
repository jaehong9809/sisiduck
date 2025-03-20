package com.a702.finafanbe.core.demanddeposit.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record InquireDemandDepositAccountRequest(
        RetrieveDemandDepositRequestHeader Header,
        String accountNo
){
    @Getter
    @AllArgsConstructor
    public static class RetrieveDemandDepositRequestHeader {
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
