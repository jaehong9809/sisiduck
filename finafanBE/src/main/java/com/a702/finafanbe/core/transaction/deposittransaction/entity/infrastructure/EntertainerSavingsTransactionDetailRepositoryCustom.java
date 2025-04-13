package com.a702.finafanbe.core.transaction.deposittransaction.entity.infrastructure;

import com.a702.finafanbe.core.transaction.deposittransaction.entity.EntertainerSavingsTransactionDetail;
import com.a702.finafanbe.core.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface EntertainerSavingsTransactionDetailRepositoryCustom {
    List<EntertainerSavingsTransactionDetail> findTopTransactionsByEntertainerIdAndPeriod(
        List<Long> depositAccountIds,
        LocalDateTime filterFromTime,
        boolean isTotal,
        int limit);

    Map<Long, User> findUsersByTransactionDetails(List<EntertainerSavingsTransactionDetail> transactions);
}
