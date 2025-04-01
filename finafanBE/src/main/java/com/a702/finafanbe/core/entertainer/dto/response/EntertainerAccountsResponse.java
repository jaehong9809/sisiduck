package com.a702.finafanbe.core.entertainer.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record EntertainerAccountsResponse(
        BigDecimal total,
        List<InquireEntertainerAccountResponse> accounts
) {
    public static EntertainerAccountsResponse of(
            BigDecimal total,
            List<InquireEntertainerAccountResponse> accounts
    ){
        return new EntertainerAccountsResponse(
                total,
                accounts
        );
    }
}
