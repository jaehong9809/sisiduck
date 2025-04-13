package com.a702.finafanbe.core.transaction.deposittransaction.entity.infrastructure;

import com.a702.finafanbe.core.transaction.deposittransaction.entity.EntertainerSavingsTransactionDetail;
import com.a702.finafanbe.core.transaction.deposittransaction.entity.QEntertainerSavingsTransactionDetail;
import com.a702.finafanbe.core.user.entity.QUser;
import com.a702.finafanbe.core.user.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EntertainerSavingsTransactionDetailRepositoryCustomImpl implements EntertainerSavingsTransactionDetailRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<EntertainerSavingsTransactionDetail> findTopTransactionsByEntertainerIdAndPeriod(
        List<Long> depositAccountIds,
        LocalDateTime filterFromTime,
        boolean isTotal,
        int limit) {

        QEntertainerSavingsTransactionDetail transaction = QEntertainerSavingsTransactionDetail.entertainerSavingsTransactionDetail;

        JPAQuery<EntertainerSavingsTransactionDetail> query = queryFactory
            .selectFrom(transaction)
            .where(transaction.depositAccountId.in(depositAccountIds));

        if (!isTotal) {
            query.where(transaction.createdAt.after(filterFromTime));
        }

        return query
            .orderBy(transaction.transactionBalance.desc())
            .limit(limit)
            .fetch();
    }

    @Override
    public Map<Long, User> findUsersByTransactionDetails(List<EntertainerSavingsTransactionDetail> transactions) {
        if (transactions.isEmpty()) {
            return Collections.emptyMap();
        }

        List<Long> userIds = transactions.stream()
            .map(EntertainerSavingsTransactionDetail::getUserId)
            .distinct()
            .collect(Collectors.toList());

        QUser user = QUser.user;
        List<User> users = queryFactory
            .selectFrom(user)
            .where(user.userId.in(userIds))
            .fetch();

        return users.stream()
            .collect(Collectors.toMap(User::getUserId, Function.identity()));
    }
}
