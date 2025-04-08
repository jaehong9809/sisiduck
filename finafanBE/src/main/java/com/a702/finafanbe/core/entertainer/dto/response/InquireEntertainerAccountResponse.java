package com.a702.finafanbe.core.entertainer.dto.response;

import com.a702.finafanbe.core.bank.entity.Bank;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InquireEntertainerAccountResponse(
        Long accountId,
        String accountNo,
        String accountName,
        BigDecimal amount,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdDate,
        Double interestRate,
        Long duration,
        Long maintenanceDays,
        String imageUrl,
        AccountInfo withdrawalAccount,
        Bank bank
) {
    public static InquireEntertainerAccountResponse of(
        Long accountId,
        String accountNo,
        String accountName,
        BigDecimal amount,
        LocalDateTime createdDate,
        Double interestRate,
        Long duration,
        Long maintenanceDays,
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
            maintenanceDays,
            imageUrl,
            withdrawalAccountInfo,
            bank
        );
    }
}
