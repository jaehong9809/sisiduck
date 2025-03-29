package com.a702.finafanbe.core.demanddeposit.application;

import static com.a702.finafanbe.global.common.exception.ErrorCode.NOT_FOUND_ACCOUNT;

import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquireDemandDepositAccountService {

    private final AccountRepository accountRepository;

    public Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(()->new BadRequestException(
            ResponseData.createResponse(NOT_FOUND_ACCOUNT)));
    }

    public Account findAccountByAccountNo(String accountNo) {
        return accountRepository.findByAccountNo(accountNo).orElseThrow(()->new BadRequestException(
            ResponseData.createResponse(NOT_FOUND_ACCOUNT)));
    }
}

