package com.a702.finafanbe.core.funding.funding.dto;

import java.util.List;

public record WithdrawTransactionRequest(
        List<Long> transactions
) {

}
