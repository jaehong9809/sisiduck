package com.a702.finafanbe.core.demanddeposit.dto.response;

import lombok.Getter;

public record UpdateDemandDepositAccountWithdrawalResponse (
    UpdateDemandDepositAccountWithdrawalResponseHeader Header,
    REC REC
) {
    @Getter
    public static class UpdateDemandDepositAccountWithdrawalResponseHeader {
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
        private String transactionUniqueNo;
        private String transactionDate;
    }
}