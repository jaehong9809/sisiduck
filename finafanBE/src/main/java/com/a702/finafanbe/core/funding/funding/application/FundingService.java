package com.a702.finafanbe.core.funding.funding.application;

import com.a702.finafanbe.core.funding.funding.dto.*;
import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;
import com.a702.finafanbe.core.funding.group.application.FundingGroupBoardService;
import com.a702.finafanbe.core.funding.group.application.FundingGroupService;
import com.a702.finafanbe.core.funding.group.entity.GroupUser;
import com.a702.finafanbe.core.funding.group.entity.Role;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.FundingGroupRepository;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.GroupBoardRepository;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.GroupUserRepository;
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
    private final FundingGroupRepository fundingGroupRepository;
    private final GroupUserRepository groupUserRepository;
    private final FundingSupportRepository fundingSupportRepository;
    private final GroupBoardRepository groupBoardRepository;

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

    @Transactional
    public void updateFundingDescription(UpdateFundingDescriptionRequest request, Long userId, Long fundingGroupId) {
        FundingGroup group = fundingGroupRepository.findById(fundingGroupId)
                .orElseThrow(() -> new RuntimeException("펀딩이 존재하지 않습니다."));
        GroupUser groupUser = groupUserRepository.findByFundingGroupIdAndUserId(fundingGroupId, userId)
                .orElseThrow(() -> new RuntimeException("해당 펀딩의 유저가 아닙니다."));
        if (!groupUser.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("방장에게만 권한이 있습니다.");
        }
        group.updateDescription(request.description());
    }

    @Transactional
    public void withdrawFunding(Long userId, Long fundingSupportId) {
        fundingSupportRepository.deleteById(fundingSupportId);
    }

    @Transactional
    public void cancelFunding(Long userId, Long fundingId) {
        checkAdminUser(userId, fundingId);
        fundingGroupRepository.deleteById(fundingId);
        fundingSupportRepository.deleteAllByFundingGroupId(fundingId);
        groupUserRepository.deleteAllByFundingGroupId(fundingId);
    }

    @Transactional
    public void terminateFunding(Long userId, Long fundingId) {
        checkAdminUser(userId, fundingId);
        if (checkFundingStatus(fundingId).equals(FundingStatus.INPROGRESS)) {
            throw new RuntimeException("펀딩이 종료되지 않았습니다.");
        }
        Long sum = groupBoardRepository.sumByFundingGroupId(fundingId);
        Long totalAmount = fundingSupportRepository.sumByFundingGroupId(fundingId);
        System.out.println(sum);
        System.out.println(totalAmount);
        if (!sum.equals(totalAmount)) {
            throw new RuntimeException("게시판에 펀딩 총액을 정확하게 입력해주세요.");
        }
        fundingGroupRepository.deleteById(fundingId);
        fundingSupportRepository.deleteAllByFundingGroupId(fundingId);
    }

    private FundingStatus checkFundingStatus(Long fundingId) {
        FundingGroup group = fundingGroupRepository.findById(fundingId)
                .orElseThrow(() -> new RuntimeException("해당 펀딩이 존재하지 않습니다."));
        return group.getStatus();
    }

    private void checkAdminUser(Long userId, Long fundingId) {
        FundingGroup group = fundingGroupRepository.findById(fundingId)
                .orElseThrow(() -> new RuntimeException("해당 펀딩이 존재하지 않습니다."));
        GroupUser groupUser = groupUserRepository.findByUserIdAndFundingGroupId(userId, fundingId)
                .orElseThrow(() -> new RuntimeException("해당 펀딩에 가입되어 있지 않습니다."));
        if (!groupUser.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("해당 권한은 방장에게 있습니다.");
        }
    }
}
