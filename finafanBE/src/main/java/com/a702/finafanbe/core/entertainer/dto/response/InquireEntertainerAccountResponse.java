package com.a702.finafanbe.core.entertainer.dto.response;

import com.a702.finafanbe.core.bank.entity.Bank;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.entertainer.entity.Entertainer;

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
        Entertainer entertainer
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
        Bank withdrawalBank,
        Entertainer entertainer
    ){
        AccountInfo withdrawalAccountInfo = new AccountInfo(
                withdrawalAccount.getAccountId(),
                withdrawalAccount.getAccountNo(),
                withdrawalAccount.getAccountName(),
                withdrawalAccount.getAmount(),
                withdrawalBank
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
            entertainer
        );
    }
}
