package com.a702.finafanbe.core.entertainer.dto.response;

import com.a702.finafanbe.core.bank.entity.Bank;

public record StarAccountResponse(
    Long userId,
    Long entertainerId,
    Long depositAccountId,
    Long withdrawalAccountId,
    Bank bank
) {
    public static StarAccountResponse of(
            Long userId,
            Long entertainerId,
            Long depositAccountId,
            Long withdrawalAccountId,
            Bank bank
    ){
        return new StarAccountResponse(
                userId,
                entertainerId,
                depositAccountId,
                withdrawalAccountId,
                bank
        );
    }
}
