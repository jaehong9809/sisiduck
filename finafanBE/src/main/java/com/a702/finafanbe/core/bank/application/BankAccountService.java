package com.a702.finafanbe.core.bank.application;

import com.a702.finafanbe.core.bank.dto.response.BankAccountConnectionResponse;
import com.a702.finafanbe.core.bank.entity.Bank;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountListResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.InquireDemandDepositAccountListResponse.REC;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.response.ResponseData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankAccountService {

    private static final String DEFAULT_EMAIL = "lsc7134@naver.com";
    private final BankService bankService;
    private final DemandDepositFacade demandDepositFacade;
    private final AccountRepository accountRepository;

    @Transactional
    public List<BankAccountConnectionResponse> connectUserAccounts(List<String> accountNos) {

        if (accountNos == null || accountNos.isEmpty()) {
            return List.of();
        }

        ResponseEntity<InquireDemandDepositAccountListResponse> response =
            demandDepositFacade.getDemandDepositListAccount(DEFAULT_EMAIL);

        List<InquireDemandDepositAccountListResponse.REC> allAccounts = response.getBody().REC();

        if (allAccounts == null || allAccounts.isEmpty()) {
            throw new BadRequestException(ResponseData.createResponse(ErrorCode.NOT_FOUND_ACCOUNT));
        }

        List<Bank> banks = bankService.findAllBanks();
        Map<String, Bank> bankCodeMap = banks.stream()
            .collect(Collectors.toMap(Bank::getBankCode, bank -> bank));
        Set<String> existingAccountNos = accountRepository.findAll().stream()
            .map(Account::getAccountNo)
            .collect(Collectors.toSet());

        List<Account> accountsToSave = new ArrayList<>();

        for (String accountNo : accountNos) {
            if(existingAccountNos.contains(accountNo)){
                continue;
            }
            Optional<REC> accountOpt = allAccounts.stream()
                .filter(acc -> acc.accountNo().equals(accountNo))
                .findFirst();
            if (accountOpt.isPresent()) {
                InquireDemandDepositAccountListResponse.REC acc = accountOpt.get();
                Bank bank = bankCodeMap.get(acc.bankCode());

                if (bank == null) {
                    log.warn("Bank not found for code: {}", acc.bankCode());
                    continue;
                }

                // 계좌 객체 생성
                Account account = Account.of(
//                    user.getUserId(),
                    1L,
                    acc.accountNo(),
                    "KRW", // 기본 통화
                    acc.accountName(),
                    "GENERAL001", // 기본 계좌 유형
                    bank.getBankId()
                );
                accountsToSave.add(account);
            }

        }

        if(!accountsToSave.isEmpty()){
            return accountRepository.saveAll(accountsToSave).stream()
                .map(account -> {
                    Bank bank = bankService.findBankById(account.getBankId());
                    return BankAccountConnectionResponse.of(
                        account,
                        bank
                    );
                })
                .collect(Collectors.toList());
        }else{
            throw new BadRequestException(ResponseData.createResponse(ErrorCode.DUPLICATE_ACCOUNT));
        }
    }
}
