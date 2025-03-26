package com.a702.finafanbe.core.savings.dto.request;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeaderIncludeUserKey;

public record RegisterSavingsAccountRequest(
    BaseRequestHeaderIncludeUserKey Header,
    String accountTypeUniqueNo,
    Long depositBalance,
    String withdrawalAccountNo
) {
}
