package com.a702.finafanbe.core.notification.infrastructure;

import com.a702.finafanbe.core.demanddeposit.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Account, Long> {
}
