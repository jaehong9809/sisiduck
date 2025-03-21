package com.a702.finafanbe.core.demanddeposit.dto.request;

import com.a702.finafanbe.global.common.header.BaseRequestHeaderIncludeUserKey;

public record UpdateDemandDepositAccountWithdrawalRequest (
        BaseRequestHeaderIncludeUserKey Header,
        String accountNo,
        Long transactionBalance,
        String transactionSummary
){

}