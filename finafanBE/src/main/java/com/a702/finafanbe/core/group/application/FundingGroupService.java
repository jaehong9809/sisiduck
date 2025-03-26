package com.a702.finafanbe.core.group.application;

import com.a702.finafanbe.core.group.dto.JoinGroupRequest;
import com.a702.finafanbe.core.group.entity.FundingGroup;
import com.a702.finafanbe.core.group.entity.FundingStatus;
import com.a702.finafanbe.core.group.entity.GroupUser;
import com.a702.finafanbe.core.group.entity.Role;
import com.a702.finafanbe.core.group.entity.infrastructure.FundingRepository;
import com.a702.finafanbe.core.group.entity.infrastructure.GroupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FundingGroupService {

    private final FundingRepository fundingGroupRepository;
    private final GroupUserRepository groupUserRepository;

    public void createFunding(Long userId, Long accountId, Long entertainerId, String name, String description, Long goalAmount) {
        FundingGroup funding = FundingGroup.create(accountId, entertainerId, name, description, goalAmount, FundingStatus.INPROGRESS);
        FundingGroup newFunding = fundingGroupRepository.save(funding);
        GroupUser groupUser = GroupUser.create(userId, newFunding.getId(), Role.ADMIN);
        fundingGroupRepository.save(funding);
        groupUserRepository.save(groupUser);
    }

    // 그룹 가입
    public void joinGroup(JoinGroupRequest request, Long userId) {

        // groupCheck();
        // 기존에 가입했던 그룹이라 있다면 그에 대한 deletedAt을 업데이트 해줘야 함
        groupUserRepository.findById(userId);
        GroupUser groupUser = GroupUser.create(userId, request.getGroupId(), Role.USER);
        // 이미 가입한 유저인지 검증 필요
        groupUserRepository.save(groupUser);
    }

    // 그룹 탈퇴
    public void leaveGroup(Long userId, Long groupId) {
        // groupCheck();
        GroupUser groupUser = groupUserRepository.findByUserIdAndFundingGroupId(userId, groupId).orElseThrow();
        groupUserRepository.delete(groupUser);
    }

    // 그룹 중도 삭제 - 펀딩 해지와 관련된 로직
    public void abortGroup(Long groupId) {
        FundingGroup funding = fundingGroupRepository.findById(groupId).orElseThrow();
        List<GroupUser> groupUsers = groupUserRepository.findAllByFundingGroupId(groupId);
        groupUserRepository.deleteAll(groupUsers);
        fundingGroupRepository.delete(funding);
    }

    private void groupCheck(Long groupId) {
        if (!fundingGroupRepository.existsById(groupId)) {
            // 예외 처리
        }
    }
}
