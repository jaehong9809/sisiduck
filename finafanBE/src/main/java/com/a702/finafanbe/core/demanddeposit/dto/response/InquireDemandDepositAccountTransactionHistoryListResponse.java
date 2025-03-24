package com.a702.finafanbe.core.demanddeposit.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;
import java.util.List;

public record InquireDemandDepositAccountTransactionHistoryListResponse(
        BaseResponseHeaderIncludeInstitutionCode Header,
        REC REC
) {

    public record REC(
        String totalCount,
        List<list> list
    ){

    }

    public record list(
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
