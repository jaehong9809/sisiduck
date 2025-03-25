package com.a702.finafanbe.core.group.entity.infrastructure;

import com.a702.finafanbe.core.group.entity.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {
    List<GroupUser> findAllByGroupId(Long groupId);

    Optional<GroupUser> findByUserIdAndGroupId(Long userId, Long groupId);
}
