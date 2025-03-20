package com.a702.finafanbe.core.demanddeposit.dto.response;

import lombok.Getter;

import java.util.List;

public record CreateAccountResponse (
    CreateDemandDepositAccountResponseHeader Header,
    REC REC
) {
    @Getter
    public static class CreateDemandDepositAccountResponseHeader {
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
        private String bankCode;
        private String accountNo;
        private Currency currency;
    }

    @Getter
    public static class Currency{
        private String currency;
        private String currencyName;
    }
}
