package com.a702.finafanbe.core.demanddeposit.dto.request;

import lombok.Getter;

public record RegisterDemandDepositRequest(
        CreateDemandDepositRequestHeader Header,
         String bankCode,
         String accountName,
         String accountDescription
){

    @Getter
    public static class CreateDemandDepositRequestHeader {
        private String apiName;
        private String transmissionDate;
        private String transmissionTime;
        private String institutionCode;
        private String fintechAppNo;
        private String apiServiceCode;
        private String institutionTransactionUniqueNo;
        private String apiKey;
    }
}
