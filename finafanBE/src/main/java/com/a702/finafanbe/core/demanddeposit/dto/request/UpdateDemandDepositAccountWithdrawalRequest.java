package com.a702.finafanbe.core.demanddeposit.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record UpdateDemandDepositAccountWithdrawalRequest (
        UpdateDemandDepositAccountWithdrawalRequestHeader Header,
        String accountNo,
        Long transactionBalance,
        String transactionSummary
){
    @Getter
    @AllArgsConstructor
    public static class UpdateDemandDepositAccountWithdrawalRequestHeader {
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