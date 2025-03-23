package com.a702.finafanbe.core.demanddeposit.dto.response;

import com.a702.finafanbe.global.common.header.BaseResponseHeaderIncludeInstitutionCode;

public record UpdateDemandDepositAccountTransferLimitResponse(
        BaseResponseHeaderIncludeInstitutionCode Header,
        REC REC
) {

    private record REC(
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
