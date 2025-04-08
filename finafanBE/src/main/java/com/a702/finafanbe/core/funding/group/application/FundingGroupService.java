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
import com.a702.finafanbe.core.user.entity.User;
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
        System.out.println(groupUser.getUserId());
    }

    public void joinFundingGroup(User user, Long groupId) {

        fundingCheck(groupId);
        groupUserRepository.findById(user.getUserId());
        GroupUser groupUser = GroupUser.create(user.getUserId(), groupId, Role.USER);
        groupUserRepository.save(groupUser);
    }

    public List<GetFundingResponse> getFundings(User user, String filter) {
        return fundingQueryRepository.findFundings(user.getUserId(), filter);
    }

    public GetFundingDetailResponse getFundingDetail(Long fundingId, User user) {
        return fundingQueryRepository.findFundingDetail(user.getUserId(), fundingId);

    }

    @Transactional
    public void updateFundingDescription(UpdateFundingDescriptionRequest request, Long fundingId, User user) {
        FundingGroup funding = fundingGroupRepository.findById(fundingId)
                .orElseThrow(() -> new RuntimeException("해당 그룹이 존재하지 않습니다."));
        funding.updateDescription(request.description());
    }

    public void leaveGroup(Long userId, Long groupId) {
        fundingCheck(groupId);
        GroupUser groupUser = groupUserRepository.findByUserIdAndFundingGroupId(userId, groupId)
                .orElseThrow(() -> new RuntimeException("해당 그룹에 가입되어 있지 않습니다."));
        groupUserRepository.delete(groupUser);
    }

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
