package com.a702.finafanbe.core.funding.funding.application;

import com.a702.finafanbe.core.funding.funding.dto.*;
import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;
import com.a702.finafanbe.core.funding.group.application.FundingGroupService;
import com.a702.finafanbe.core.funding.group.entity.GroupBoard;
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
    private final FundingGroupRepository fundingGroupRepository;
    private final GroupUserRepository groupUserRepository;
    private final FundingPendingTransactionRepository fundingPendingTransactionRepository;
    private final GroupBoardRepository groupBoardRepository;
    private final FundingQueryRepository fundingQueryRepository;

    // 펀딩 생성
    @Transactional
    public void createFunding(CreateFundingRequest request, User user) {

        // ApiCreateAccountResponse response = apiSavingsAccountService.createFundingAccount(userId);
        // System.out.println("API Answer : " + response.accountTypeUniqueNo() + " " + response.accountNo());
        String accountNo = "1111";
        String accountTypeUniqueNo = "001-1-f5f1f9ee427d47";
        SavingsAccount fundingAccount = fundingAccountService.createFundingAccount(request, user.getUserId(), accountNo, accountTypeUniqueNo);
        //System.out.println("fundingAccount Answer : " + fundingAccount.getAccountNickname());

        fundingGroupService.createFundingGroup(request, user.getUserId(), fundingAccount.getId());
    }

    @Transactional(readOnly = true)
    public List<GetFundingResponse> getFunding(User user, String filter) {
        return fundingGroupService.getFundings(user, filter);
    }

    @Transactional(readOnly = true)
    public GetFundingDetailResponse getFundingDetail(Long fundingId, User user) {
        return fundingGroupService.getFundingDetail(fundingId, user);
    }

    @Transactional(readOnly = true)
    public List<GetFundingPendingTransactionResponse> getFundingPendingTransaction (User user, Long fundingId, String filter) {
        return fundingQueryRepository.getTransaction(user.getUserId(), fundingId, filter);
    }

    @Transactional
    public void joinFunding(User user, Long fundingId) {
        if (groupUserRepository.existsByFundingGroupIdAndUserId(fundingId, user.getUserId())) {
            throw new RuntimeException("이미 가입한 펀딩입니다.");
        }
        fundingGroupService.joinFundingGroup(user, fundingId);
    }

    @Transactional
    public void supportFunding(FundingPendingTransactionRequest request, User user, Long fundingId) {
        checkFundingMember(fundingId, user.getUserId());
        if(!checkFundingStatus(fundingId).equals(FundingStatus.INPROGRESS)) {
            throw new RuntimeException("펀딩이 진행 중일 때만 입금할 수 있습니다.");
        }
        FundingPendingTransaction transaction = FundingPendingTransaction.create(request, user.getUserId(), fundingId, user.getName());
        fundingApplicationRepository.save(transaction);
    }

    @Transactional
    public void updateFundingDescription(UpdateFundingDescriptionRequest request, User user, Long fundingId) {
        checkAdminUser(user.getUserId(), fundingId);
        FundingGroup group = fundingGroupRepository.findById(fundingId)
                .orElseThrow(() -> new RuntimeException("펀딩이 존재하지 않습니다."));
        group.updateDescription(request.description());
    }

    @Transactional
    public void resignFunding(User user, Long fundingId) {
        if (!checkFundingStatus(fundingId).equals(FundingStatus.INPROGRESS)) {
            throw new RuntimeException("펀딩이 진행 중일 때만 입금을 취소할 수 있습니다.");
        }
        GroupUser groupUser = groupUserRepository.findByFundingGroupIdAndUserId(fundingId, user.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 펀딩에 가입되어 있지 않습니다."));
        List<FundingPendingTransaction> transactions = fundingPendingTransactionRepository.findAllByFundingIdAndUserId(fundingId, user.getUserId());
        fundingPendingTransactionRepository.deleteAll(transactions);
        groupUserRepository.delete(groupUser);
    }

    @Transactional
    public void withdrawFunding(User user, Long fundingId, WithdrawTransactionRequest request) {

        if(!checkFundingStatus(fundingId).equals(FundingStatus.INPROGRESS)) {
            throw new RuntimeException("펀딩이 진행 중일 때만 입금을 취소할 수 있습니다.");
        }
        for (Long id : request.transactions()) {
            fundingPendingTransactionRepository.deleteById(id);
        }

    }

    @Transactional
    public void cancelFunding(User user, Long fundingId, CancelFundingRequest request) {
        checkAdminUser(user.getUserId(), fundingId);
        if (!checkFundingStatus(fundingId).equals(FundingStatus.INPROGRESS)) {
            throw new RuntimeException("펀딩이 진행 중일 때만 중단할 수 있습니다.");
        }
        FundingGroup funding = fundingGroupRepository.findById(fundingId)
                .orElseThrow(() -> new RuntimeException("펀딩이 존재하지 않습니다."));

        funding.updateFundingStatus(FundingStatus.CANCELED);
        fundingPendingTransactionRepository.deleteAllByFundingId(fundingId);
        groupUserRepository.deleteAllByFundingGroupId(fundingId);
    }

    @Transactional
    public void terminateFunding(User user, Long fundingId) {
        checkAdminUser(user.getUserId(), fundingId);
        if (!checkFundingStatus(fundingId).equals(FundingStatus.SUCCESS)) {
            throw new RuntimeException("펀딩이 종료되지 않았습니다.");
        }
        GroupBoard board = groupBoardRepository.findByFundingId(fundingId)
                .orElseThrow(() -> new RuntimeException("해당 펀딩에 대한 증빙 서류가 제출되지 않았습니다."));
        Long sum = groupBoardRepository.sumBoardAmount(board.getId());
        Long totalAmount = fundingPendingTransactionRepository.sumByFundingId(fundingId);
        if (!sum.equals(totalAmount)) {
            throw new RuntimeException("게시판에 펀딩 총액을 정확하게 입력해주세요.");
        }
        findFunding(fundingId).updateFundingStatus(FundingStatus.TERMINATED);
        fundingPendingTransactionRepository.deleteAllByFundingId(fundingId);
    }

    private FundingGroup findFunding(Long fundingId) {
        return fundingGroupRepository.findById(fundingId)
                .orElseThrow(() -> new RuntimeException("해당 펀딩이 존재하지 않습니다."));
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

    private void checkFundingMember(Long fundingId, Long userId) {
        if (!groupUserRepository.existsByFundingGroupIdAndUserId(fundingId, userId)) {
            throw new RuntimeException("펀딩에 가입되어 있지 않습니다.");
        }
    }
}