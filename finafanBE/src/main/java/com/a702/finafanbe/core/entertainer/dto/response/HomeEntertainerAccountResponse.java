package com.a702.finafanbe.core.entertainer.dto.response;

import java.math.BigDecimal;

public record HomeEntertainerAccountResponse(
        Long starAccountId,
        String starName,
        String starImageUrl,
        String accountNo,
        BigDecimal amount
) {

    public static HomeEntertainerAccountResponse of(
            Long starAccountId,
            String starName,
            String starImageUrl,
            String accountNo,
            BigDecimal amount
    ){
        return new HomeEntertainerAccountResponse(
                starAccountId,
                starName,
                starImageUrl,
                accountNo,
                amount
        );
    }
}
