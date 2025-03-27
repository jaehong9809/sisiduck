package com.a702.finafanbe.core.savings.application.funding;

import com.a702.finafanbe.core.group.application.FundingGroupService;
import com.a702.finafanbe.core.group.entity.infrastructure.FundingQueryRepository;
import com.a702.finafanbe.core.group.entity.infrastructure.GroupUserRepository;
import com.a702.finafanbe.core.savings.application.ApiSavingsAccountService;
import com.a702.finafanbe.core.savings.dto.fundingDto.*;
import com.a702.finafanbe.core.savings.entity.FundingApplication;
import com.a702.finafanbe.core.savings.entity.infrastructure.FundingApplicationRepository;
import com.a702.finafanbe.core.savings.entity.SavingsAccount;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundingService {

    private final ApiSavingsAccountService apiSavingsAccountService;

    private final FundingAccountService fundingAccountService;

    private final FundingGroupService fundingGroupService;

    private final FundingApplicationRepository fundingApplicationRepository;

    private final FundingQueryRepository fundingQueryRepository;

    private final GroupUserRepository groupUserRepository;

    private final UserRepository userRepository;

    // 펀딩 생성
    @Transactional
    public void createFunding(CreateFundingRequest request, Long userId) {
        // 금융 api에 계좌 만듦
        // String account = apiSavingsAccountService.createAccount(request);
        String account = "111111111";

        // 우리 db에 계좌 정보 저장
        SavingsAccount fundingAccount = fundingAccountService.createFundingAccount(request, userId, account);

        // 펀딩 관련 생성
        fundingGroupService.createFunding(userId, fundingAccount.getId(), request.getEntertainerId(), request.getAccountNickname(), request.getDescription(), request.getGoalAmount(), request.getFundingExpiryDate());
    }

    // 펀딩 리스트 조회
    @Transactional(readOnly = true)
    public List<GetFundingResponse> getFunding(Long userId, String filter) {
        return fundingQueryRepository.findFundings(userId, filter); // 동적 QueryDSL로 처리
    }

    // 펀딩 상세 조회
    @Transactional(readOnly = true)
    public GetFundingDetailResponse getFundingDetail(Long fundingGroupId, Long userId) {
        boolean isJoined = groupUserRepository.existsByFundingGroupIdAndUserId(fundingGroupId, userId);

        GetFundingDetailResponse response = fundingQueryRepository.findFundingDetail(fundingGroupId);
        response.setParticipated(isJoined);

        List<FundingApplicationResponse> applicationResponse = new ArrayList<>();

        if (isJoined) {
            List<FundingApplication> applications = fundingApplicationRepository.findAllByFundingGroupIdAndDeletedAtIsNull(fundingGroupId);
            for (FundingApplication a : applications) {
                User user = userRepository.findById(a.getUserId()).orElseThrow();
                String userName = user.getName();
                FundingApplicationResponse res = new FundingApplicationResponse(userName, a.getBalance(), a.getContent(), a.getCreatedAt());
                applicationResponse.add(res);
            }
            response.setFundingApplication(applicationResponse);

        } else {
            response.setFundingApplication(Collections.emptyList());
        }

        return response;
    }

    // 펀딩 가입
    @Transactional
    public void joinFunding(Long userId, Long groupId) {
        fundingGroupService.joinFundingGroup(userId, groupId);
    }

    // 펀딩 입금
    @Transactional
    public void participateFunding(ParticipateFundingRequest request, Long userId, Long fundingGroupId) {
        // 추후 이름 마스킹 처리
        User user = userRepository.findById(userId).orElseThrow();
        System.out.println(user.getUserId());
        FundingApplication appliance = FundingApplication.create(userId, fundingGroupId, request.getAccountId(), request.getBalance(), request.getContent(), user.getName());
        fundingApplicationRepository.save(appliance);
    }
}
