package com.a702.finafanbe.core.entertainer.dto.response;

import com.a702.finafanbe.core.bank.entity.Bank;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import java.math.BigDecimal;

public record WithdrawalAccountResponse(
    Long accountId,
    String accountNo,
    String bankName,
    String accountName,
    BigDecimal balance
) {
    public static WithdrawalAccountResponse of(Account account, Bank bank) {
        return new WithdrawalAccountResponse(
            account.getAccountId(),
            account.getAccountNo(),
            bank.getBankName(),
            account.getAccountName(),
            account.getAmount()
        );
    }
}
