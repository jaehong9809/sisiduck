package com.a702.finafanbe.core.bank.dto.response;

import com.a702.finafanbe.core.bank.entity.Bank;
import com.a702.finafanbe.core.demanddeposit.entity.Account;

public record BankAccountConnectionResponse(
    Long accountId,
    String accountNo,
    Bank bank
) {
    public static BankAccountConnectionResponse from(Account account, Bank bank) {
        return new BankAccountConnectionResponse(
            account.getAccountId(),
            account.getAccountNo(),
            bank
        );
    }
}
