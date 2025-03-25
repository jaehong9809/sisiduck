package com.a702.finafanbe.core.demanddeposit.dto.request;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeaderIncludeUserKey;

public record InquireDemandDepositAccountTransactionHistoryListRequest(
        BaseRequestHeaderIncludeUserKey Header,
        String accountNo,
        String startDate,
        String endDate,
        String transactionType,
        String orderByType
) {

}
