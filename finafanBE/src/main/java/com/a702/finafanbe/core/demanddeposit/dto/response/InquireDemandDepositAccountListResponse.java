package com.a702.finafanbe.core.demanddeposit.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;

import java.util.List;

public record InquireDemandDepositAccountListResponse(
    BaseResponseHeaderIncludeInstitutionCode Header,
    List<REC> REC
) {
    public record REC (
        String bankCode,
        String bankName,
        String userName,
        String accountNo,
        String accountName,
        String accountTypeCode,
        String accountTypeName,
        String accountCreatedDate,
        String accountExpiryDate,
        Long dailyTransferLimit,
        Long oneTimeTransferLimit,
        Long accountBalance,
        String lastTransactionDate,
        String currency
    ){

    }
}
