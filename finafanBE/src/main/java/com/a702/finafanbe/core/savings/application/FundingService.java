package com.a702.finafanbe.core.savings.application;

import com.a702.finafanbe.core.group.application.FundingGroupService;
import com.a702.finafanbe.core.savings.dto.CreateFundingRequest;
import com.a702.finafanbe.core.savings.entity.SavingsAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FundingService {

    private final ApiSavingsAccountService apiSavingsAccountService;

    private final FundingAccountService fundingAccountService;

    private final FundingGroupService fundingGroupService;

    // 펀딩 생성
    @Transactional
    public void createFunding(CreateFundingRequest request, Long userId) {
        // 금융 api에 계좌 만듦
        // String account = apiSavingsAccountService.createAccount(request);
        String account = "111111111";

        // 우리 db에 계좌 정보 저장
        SavingsAccount fundingAccount = fundingAccountService.createFundingAccount(request, userId, account);

        // 펀딩 관련 생성
        fundingGroupService.createFunding(userId, fundingAccount.getId(), request.getEntertainerId(), request.getAccountNickname(), request.getDescription(), request.getGoalAmount());
    }

    @Transactional(readOnly = true)
    public void getFunding() {

    }

    @Transactional
    public void joinFunding() {
        // groupService.joinGroup();
    }

    // 펀딩 가입

    // 펀딩 입금

    // 펀딩 탈퇴

    // 펀딩
}
