package com.a702.finafanbe.core.group.application;

import com.a702.finafanbe.core.group.dto.JoinGroupRequest;
import com.a702.finafanbe.core.group.entity.Group;
import com.a702.finafanbe.core.group.entity.GroupUser;
import com.a702.finafanbe.core.group.entity.Role;
import com.a702.finafanbe.core.group.entity.infrastructure.GroupRepository;
import com.a702.finafanbe.core.group.entity.infrastructure.GroupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupUserRepository groupUserRepository;

    // 그룹 생성 - 펀딩 통장 생성과 같이 가야 하는 로직
    @Transactional
    public void createGroup(Long userId, Long entertainerId, String name, String description) {
        Group group = Group.create(entertainerId, name, description);
        Group newGroup = groupRepository.save(group);
        GroupUser groupUser = GroupUser.create(userId, newGroup.getGroupId(), Role.ADMIN);
        groupRepository.save(group);
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
        GroupUser groupUser = groupUserRepository.findByUserIdAndGroupId(userId, groupId).orElseThrow();
        groupUserRepository.delete(groupUser);
    }

    // 그룹 중도 삭제 - 펀딩 해지와 관련된 로직
    public void abortGroup(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        List<GroupUser> groupUsers = groupUserRepository.findAllByGroupId(groupId);
        groupUserRepository.deleteAll(groupUsers);
        groupRepository.delete(group);
    }

    private void groupCheck(Long groupId) {
        if (!groupRepository.existsById(groupId)) {
            // 예외 처리
        }
    }
}
