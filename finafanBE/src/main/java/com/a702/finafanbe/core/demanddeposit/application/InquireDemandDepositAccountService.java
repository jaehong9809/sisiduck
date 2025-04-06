package com.a702.finafanbe.core.demanddeposit.application;

import static com.a702.finafanbe.global.common.exception.ErrorCode.NOT_FOUND_ACCOUNT;

import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.response.ResponseData;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InquireDemandDepositAccountService {

    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public Account findAccountById(Long accountId) {
        return accountRepository.findByAccountId(accountId).orElseThrow(()->new BadRequestException(
            ResponseData.createResponse(NOT_FOUND_ACCOUNT)));
    }

    @Transactional(readOnly = true)
    public Account findAccountByAccountNo(String accountNo) {
        return accountRepository.findByAccountNo(accountNo).orElseThrow(()->new BadRequestException(
            ResponseData.createResponse(NOT_FOUND_ACCOUNT)));
    }

    public List<Account> findAccountByUserId(Long userId) {
        return accountRepository.findByUserId(userId).orElseThrow(()->new BadRequestException(ResponseData.createResponse(
            ErrorCode.NOT_FOUND_ACCOUNT)));
    }

    public Set<String> findAllAccountsNo() {
        return accountRepository.findAll().stream()
            .map(Account::getAccountNo)
            .collect(Collectors.toSet());
    }
}

