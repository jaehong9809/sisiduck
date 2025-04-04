package com.a702.finafanbe.core.funding.group.application;

import com.a702.finafanbe.core.funding.funding.dto.CreateFundingRequest;
import com.a702.finafanbe.core.funding.funding.dto.GetFundingDetailResponse;
import com.a702.finafanbe.core.funding.funding.dto.GetFundingResponse;
import com.a702.finafanbe.core.funding.funding.dto.UpdateFundingDescriptionRequest;
import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;
import com.a702.finafanbe.core.funding.group.entity.GroupUser;
import com.a702.finafanbe.core.funding.group.entity.Role;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.FundingGroupRepository;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.FundingQueryRepository;
import com.a702.finafanbe.core.funding.group.entity.infrastructure.GroupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FundingGroupService {

    private final FundingGroupRepository fundingGroupRepository;
    private final GroupUserRepository groupUserRepository;
    private final FundingQueryRepository fundingQueryRepository;

    public void createFundingGroup(CreateFundingRequest request, Long userId, Long accountId) {
        FundingGroup funding = FundingGroup.create(request, accountId, FundingStatus.INPROGRESS);
        FundingGroup newFunding = fundingGroupRepository.save(funding);
        GroupUser groupUser = GroupUser.create(userId, newFunding.getId(), Role.ADMIN);
        fundingGroupRepository.save(funding);
        groupUserRepository.save(groupUser);
    }

    // 그룹 가입
    public void joinFundingGroup(Long userId, Long groupId) {

        fundingCheck(groupId);
        // 기존에 가입했던 그룹이라 있다면 그에 대한 deletedAt을 업데이트 해줘야 함
        groupUserRepository.findById(userId);
        GroupUser groupUser = GroupUser.create(userId, groupId, Role.USER);
        // 이미 가입한 유저인지 검증 필요
        groupUserRepository.save(groupUser);
    }

    public List<GetFundingResponse> getFundings(Long userId, String filter) {
        return fundingQueryRepository.findFundings(userId, filter);
    }

    public GetFundingDetailResponse getFundingDetail(Long fundingId, Long userId) {
        return fundingQueryRepository.findFundingDetail(userId, fundingId);

    }

    @Transactional
    public void updateFundingDescription(UpdateFundingDescriptionRequest request, Long fundingId, Long userId) {
        FundingGroup funding = fundingGroupRepository.findById(fundingId)
                .orElseThrow(() -> new RuntimeException("해당 그룹이 존재하지 않습니다."));
        funding.updateDescription(request.description());
    }

    // 그룹 탈퇴
    public void leaveGroup(Long userId, Long groupId) {
        fundingCheck(groupId);
        GroupUser groupUser = groupUserRepository.findByUserIdAndFundingGroupId(userId, groupId)
                .orElseThrow(() -> new RuntimeException("해당 그룹에 가입되어 있지 않습니다."));
        groupUserRepository.delete(groupUser);
    }

    // 그룹 중도 삭제 - 펀딩 해지와 관련된 로직
    public void abortGroup(Long fundingId) {
        fundingCheck(fundingId);
        groupUserRepository.deleteAllByFundingGroupId(fundingId);
        fundingGroupRepository.deleteById(fundingId);
    }

    private void fundingCheck(Long fundingId) {
        if (!fundingGroupRepository.existsById(fundingId)) {
            throw new RuntimeException("존재하지 않는 펀딩입니다.");
        }
    }
}
