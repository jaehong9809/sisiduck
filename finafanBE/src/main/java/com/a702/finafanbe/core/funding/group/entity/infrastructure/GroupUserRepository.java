package com.a702.finafanbe.core.funding.group.entity.infrastructure;

import com.a702.finafanbe.core.funding.group.entity.GroupUser;
import com.a702.finafanbe.core.funding.group.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {
    List<GroupUser> findAllByFundingGroupId(Long groupId);

    Optional<GroupUser> findByUserIdAndFundingGroupId(Long userId, Long groupId);

    Optional<GroupUser> findByFundingGroupIdAndUserId(Long fundingGroupId, Long userId);

    Optional<GroupUser> findByFundingGroupIdAndRole(Long fundingId, Role role)

    void deleteAllByFundingGroupId(Long fundingId);
}
