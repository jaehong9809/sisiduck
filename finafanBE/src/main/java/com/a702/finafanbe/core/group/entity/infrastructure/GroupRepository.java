package com.a702.finafanbe.core.group.entity.infrastructure;

import com.a702.finafanbe.core.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
