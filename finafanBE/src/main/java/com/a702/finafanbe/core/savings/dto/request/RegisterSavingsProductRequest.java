package com.a702.finafanbe.core.savings.dto.request;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeader;

public record RegisterSavingsProductRequest(
    BaseRequestHeader Header,
    String bankCode,
    String accountName,
    String accountDescription,
    String subscriptionPeriod,
    Long minSubscriptionBalance,
    Long maxSubscriptionBalance,
    Double interestRate,
    String rateDescription
) {

}
