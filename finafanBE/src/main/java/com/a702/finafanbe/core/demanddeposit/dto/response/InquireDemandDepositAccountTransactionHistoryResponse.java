package com.a702.finafanbe.core.demanddeposit.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;

public record InquireDemandDepositAccountTransactionHistoryResponse(
    BaseResponseHeaderIncludeInstitutionCode Header,
    REC REC
) {

    private record REC(
        Long transactionUniqueNo,
        String transactionDate,
        String transactionTime,
        String transactionType,
        String transactionTypeName,
        String transactionAccountNo,
        Long transactionBalance,
        Long transactionAfterBalance,
        String transactionSummary,
        String transactionMemo
    ){

    }
}
