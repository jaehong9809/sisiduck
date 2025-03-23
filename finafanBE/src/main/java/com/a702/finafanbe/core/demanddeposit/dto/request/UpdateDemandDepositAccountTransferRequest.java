package com.a702.finafanbe.core.demanddeposit.dto.request;

import com.a702.finafanbe.global.common.header.BaseRequestHeaderIncludeUserKey;

public record UpdateDemandDepositAccountTransferRequest(
    BaseRequestHeaderIncludeUserKey Header,
    String depositAccountNo,
    String depositTransactionSummary,
    Long transactionBalance,
    String withdrawalAccountNo,
    String withdrawalTransactionSummary
) {

}
