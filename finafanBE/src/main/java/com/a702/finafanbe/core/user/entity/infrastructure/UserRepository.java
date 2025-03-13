package com.a702.finafanbe.core.user.entity.infrastructure;

import com.a702.finafanbe.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
