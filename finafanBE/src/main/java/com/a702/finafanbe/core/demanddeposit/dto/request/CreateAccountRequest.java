package com.a702.finafanbe.core.demanddeposit.dto.request;

import lombok.Getter;

public record CreateAccountRequest (
    CreateDemandDepositAccountRequestHeader Header,
    String accountTypeUniqueNo
){

    @Getter
    public static class CreateDemandDepositAccountRequestHeader {
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
