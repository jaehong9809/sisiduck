package com.a702.finafanbe.core.entertainer.dto.response;

import com.a702.finafanbe.core.bank.entity.Bank;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InquireEntertainerAccountResponse(
        Long accountId,
        String accountNo,
        String accountName,
        BigDecimal amount,
        LocalDateTime createdDate,
        Double interestRate,
        Long duration,
        String imageUrl,
        AccountInfo withdrawalAccount,
        Bank bank,
        Bank withdrawalBank
) {
    public static InquireEntertainerAccountResponse of(
        Long accountId,
        String accountNo,
        String accountName,
        BigDecimal amount,
        LocalDateTime createdDate,
        Double interestRate,
        Long duration,
        String imageUrl,
        Account withdrawalAccount,
        Bank bank,
        Bank withdrawalBank
    ){
        AccountInfo withdrawalAccountInfo = new AccountInfo(
                withdrawalAccount.getAccountId(),
                withdrawalAccount.getAccountNo(),
                withdrawalAccount.getAccountName(),
                withdrawalAccount.getAmount(),
                withdrawalBank.getBankName(),
                withdrawalBank.getBankCode()
        );

        return new InquireEntertainerAccountResponse(
            accountId,
            accountNo,
            accountName,
            amount,
            createdDate,
            interestRate,
            duration,
            imageUrl,
            withdrawalAccountInfo,
            bank,
            withdrawalBank
        );
    }
}
