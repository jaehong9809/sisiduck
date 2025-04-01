package com.a702.finafanbe.core.funding.funding.application;

import com.a702.finafanbe.core.funding.funding.dto.CreateFundingRequest;
import com.a702.finafanbe.core.funding.funding.dto.FundingSupportRequest;
import com.a702.finafanbe.core.funding.funding.dto.GetFundingDetailResponse;
import com.a702.finafanbe.core.funding.funding.dto.GetFundingResponse;
import com.a702.finafanbe.core.funding.group.application.FundingGroupService;
import com.a702.finafanbe.core.savings.application.ApiSavingsAccountService;
import com.a702.finafanbe.core.funding.funding.entity.FundingSupport;
import com.a702.finafanbe.core.funding.funding.entity.infrastructure.FundingSupportRepository;
import com.a702.finafanbe.core.savings.entity.SavingsAccount;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FundingService {

    private final ApiSavingsAccountService apiSavingsAccountService;

    private final FundingAccountService fundingAccountService;

    private final FundingGroupService fundingGroupService;

    private final FundingSupportRepository fundingApplicationRepository;

    private final UserRepository userRepository;

    // 펀딩 생성
    @Transactional
    public void createFunding(CreateFundingRequest request, Long userId) {

        Random random = new Random();
        int rand = 100 + random.nextInt(899);

        String account = "111111111"+ String.valueOf(rand);
        //String account = apiSavingsAccountService.createAccount(request);
        SavingsAccount fundingAccount = fundingAccountService.createFundingAccount(request, userId, account);
        fundingGroupService.createFundingGroup(request, userId, fundingAccount.getId());
    }

    // 펀딩 리스트 조회
    @Transactional(readOnly = true)
    public List<GetFundingResponse> getFunding(Long userId, String filter) {
        return fundingGroupService.getFundings(userId, filter);
    }

    // 펀딩 상세 조회
    @Transactional(readOnly = true)
    public GetFundingDetailResponse getFundingDetail(Long fundingId, Long userId) {
        return fundingGroupService.getFundingDetail(fundingId, userId);
    }

    // 펀딩 가입
    @Transactional
    public void joinFunding(Long userId, Long groupId) {
        fundingGroupService.joinFundingGroup(userId, groupId);
    }

    // 펀딩 입금
    @Transactional
    public void supportFunding(FundingSupportRequest request, Long userId, Long fundingGroupId) {
        User user = userRepository.findById(userId).orElseThrow();
        FundingSupport appliance = FundingSupport.create(request, userId, fundingGroupId, user.getName());
        fundingApplicationRepository.save(appliance);
    }

}
