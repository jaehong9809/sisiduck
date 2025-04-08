package com.a702.finafanbe.core.user.entity.infrastructure;

import com.a702.finafanbe.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findBySocialEmail(String socialEmail);

    List<User> findByUserIdIn(List<Long> userIds);

    boolean existsBySocialEmail(String userEmail);

    Optional<User> findByUserId(Long userId);
}
