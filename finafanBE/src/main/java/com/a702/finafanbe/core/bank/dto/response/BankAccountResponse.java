package com.a702.finafanbe.core.bank.dto.response;

import com.a702.finafanbe.core.bank.entity.Bank;

public record BankAccountResponse(
    String accountNo,
    Bank bank
) {
    public static BankAccountResponse of(String accountNo, Bank bank) {
        return new BankAccountResponse(
            accountNo,
            bank
        );
    }
}
