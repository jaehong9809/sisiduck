package com.a702.finafanbe.core.funding.funding.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.ApiCreateAccountResponse;
import com.a702.finafanbe.core.funding.funding.dto.*;
import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;
import com.a702.finafanbe.core.funding.group.application.FundingGroupService;
import com.a702.finafanbe.core.funding.group.entity.GroupUser;
import com.a702.finafanbe.core.funding.group.entity.Role;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.FundingGroupRepository;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.FundingQueryRepository;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.GroupBoardRepository;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.GroupUserRepository;
import com.a702.finafanbe.core.savings.application.ApiSavingsAccountService;
import com.a702.finafanbe.core.funding.funding.entity.FundingPendingTransaction;
import com.a702.finafanbe.core.funding.funding.entity.infrastructure.FundingPendingTransactionRepository;
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
    private final FundingPendingTransactionRepository fundingApplicationRepository;
    private final UserRepository userRepository;
    private final FundingGroupRepository fundingGroupRepository;
    private final GroupUserRepository groupUserRepository;
    private final FundingPendingTransactionRepository fundingPendingTransactionRepository;
    private final GroupBoardRepository groupBoardRepository;
    private final FundingQueryRepository fundingQueryRepository;

    // 펀딩 생성
    @Transactional
    public void createFunding(CreateFundingRequest request, Long userId) {

        // ApiCreateAccountResponse response = apiSavingsAccountService.createFundingAccount(userId);
        // System.out.println("API Answer : " + response.accountTypeUniqueNo() + " " + response.accountNo());

        String accountNo = "1111";
        String accountTypeUniqueNo = "001-1-f5f1f9ee427d47";
        SavingsAccount fundingAccount = fundingAccountService.createFundingAccount(request, userId, accountNo, accountTypeUniqueNo);
        //System.out.println("fundingAccount Answer : " + fundingAccount.getAccountNickname());

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

    @Transactional(readOnly = true)
    public List<GetFundingPendingTransactionResponse> getFundingPendingTransaction (Long userId, Long fundingId, String filter) {
        return fundingQueryRepository.getTransaction(userId, fundingId, filter);
    }

    // 펀딩 가입
    @Transactional
    public void joinFunding(Long userId, Long groupId) {
        fundingGroupService.joinFundingGroup(userId, groupId);
    }

    // 펀딩 입금
    @Transactional
    public void supportFunding(FundingPendingTransactionRequest request, Long userId, Long fundingId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));
        if(!checkFundingStatus(fundingId).equals(FundingStatus.INPROGRESS)) {
            throw new RuntimeException("펀딩이 진행 중일 때만 입금할 수 있습니다.");
        }
        FundingPendingTransaction transaction = FundingPendingTransaction.create(request, userId, fundingId, user.getName());
        fundingApplicationRepository.save(transaction);
    }

    // 펕딩 안내 설명 글 변경
    @Transactional
    public void updateFundingDescription(UpdateFundingDescriptionRequest request, Long userId, Long fundingId) {
        FundingGroup group = fundingGroupRepository.findById(fundingId)
                .orElseThrow(() -> new RuntimeException("펀딩이 존재하지 않습니다."));
        GroupUser groupUser = groupUserRepository.findByFundingGroupIdAndUserId(fundingId, userId)
                .orElseThrow(() -> new RuntimeException("해당 펀딩의 유저가 아닙니다."));
        if (!groupUser.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("방장에게만 권한이 있습니다.");
        }
        group.updateDescription(request.description());
    }

    // 펀딩 입금 취소
    @Transactional
    public void withdrawFunding(Long userId, Long fundingId, WithdrawTransactionRequest request) {
        if(!checkFundingStatus(fundingId).equals(FundingStatus.INPROGRESS)) {
            throw new RuntimeException("펀딩이 진행 중일 때만 입금을 취소할 수 있습니다.");
        }
        for (Long id : request.transactions()) {
            fundingPendingTransactionRepository.deleteById(id);
        }

    }

    // 펀딩 중도 해지
    @Transactional
    public void cancelFunding(Long userId, Long fundingId, CancelFundingRequest request) {
        checkAdminUser(userId, fundingId);
        if (!checkFundingStatus(fundingId).equals(FundingStatus.INPROGRESS)) {
            throw new RuntimeException("펀딩이 진행 중일 때만 중단할 수 있습니다.");
        }
        FundingGroup funding = fundingGroupRepository.findById(fundingId)
                .orElseThrow(() -> new RuntimeException("펀딩이 존재하지 않습니다."));

        funding.updateFundingStatus(FundingStatus.CANCELED);
        fundingPendingTransactionRepository.deleteAllByFundingId(fundingId);
        groupUserRepository.deleteAllByFundingGroupId(fundingId);
    }

    // 펀딩 공식 종료
    @Transactional
    public void terminateFunding(Long userId, Long fundingId) {
        checkAdminUser(userId, fundingId);
        if (!checkFundingStatus(fundingId).equals(FundingStatus.SUCCESS)) {
            throw new RuntimeException("펀딩이 종료되지 않았습니다.");
        }
        Long sum = groupBoardRepository.sumByFundingGroupId(fundingId);
        Long totalAmount = fundingPendingTransactionRepository.sumByFundingId(fundingId);
        System.out.println(sum);
        System.out.println(totalAmount);
        if (!sum.equals(totalAmount)) {
            throw new RuntimeException("게시판에 펀딩 총액을 정확하게 입력해주세요.");
        }
        findFundingGroup(fundingId).updateFundingStatus(FundingStatus.TERMINATED);
        fundingPendingTransactionRepository.deleteAllByFundingId(fundingId);
    }

    private FundingGroup findFundingGroup(Long fundingId) {
        return fundingGroupRepository.findById(fundingId)
                .orElseThrow(() -> new RuntimeException("해당 펀딩이 존재하지 않습니다."));
    }

    // 펀딩 입금 신청, 입금신청취소, 중도해지는 펀딩 INPROGRESS에만 가능
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
