package com.a702.finafanbe.core.demanddeposit.dto.response;

import com.a702.finafanbe.global.common.header.BaseResponseHeaderIncludeInstitutionCode;
import java.util.List;

public record UpdateDemandDepositAccountTransferResponse(
    BaseResponseHeaderIncludeInstitutionCode Header,
    List<REC> REC
) {

    public record REC(
        Long transactionUniqueNo,
        String accountNo,
        String transactionDate,
        String transactionType,
        String transactionTypeName,
        String transactionAccountNo
    ){

    }
}
