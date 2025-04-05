package com.a702.finafanbe.core.bank.application;

import com.a702.finafanbe.core.bank.dto.response.BankAccountConnectionResponse;
import com.a702.finafanbe.core.bank.entity.Bank;
import com.a702.finafanbe.core.demanddeposit.application.InquireDemandDepositAccountService;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankConnectService {

    private final AccountRepository accountRepository;
    private final BankService bankService;
    private final InquireDemandDepositAccountService inquireDemandDepositAccountService;

    /**
     * 사용자의 계좌 중에서 특정 은행들에 속한 계좌 정보를 조회합니다.
     *
     * @param user 현재 사용자
     * @param bankIds 조회할 은행 ID 목록
     * @return 계좌 연결 응답 목록
     */
    @Transactional(readOnly = true)
    public List<BankAccountConnectionResponse> findUserAccountsByBanks(User user, List<Long> bankIds) {
        Optional<List<Account>> userAccountsOpt = inquireDemandDepositAccountService.findAccountByUserId(
            user.getUserId());

        if (userAccountsOpt.isEmpty()) {
            return List.of();
        }

        List<Account> userAccounts = userAccountsOpt.get();

        List<Account> filteredAccounts = userAccounts.stream()
            .filter(account -> bankIds.contains(account.getBankId()))
            .collect(Collectors.toList());

        Map<Long, Bank> bankMap = bankIds.stream()
            .collect(Collectors.toMap(
                bankId -> bankId,
                bankService::findBankById
            ));

        return filteredAccounts.stream()
            .map(account -> BankAccountConnectionResponse.from(
                account,
                bankMap.get(account.getBankId())
            ))
            .collect(Collectors.toList());
    }
}