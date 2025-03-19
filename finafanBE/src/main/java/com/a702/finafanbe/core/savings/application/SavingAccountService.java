package com.a702.finafanbe.core.savings.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavingAccountService {

    private final SavingAccountService savingAccountService;

    // 적금, 펀딩의 공통적인 기능
    // 계좌 개설
    // 입금
    // 계좌 별명 변경
    public void createAccount() {

    }

    public void depositMoney() {

    }

    public void updateAccountName() {

    }

}
