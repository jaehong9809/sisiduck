package com.a702.finafanbe.core.batch.entity.Infrastructure;

import com.a702.finafanbe.core.funding.funding.entity.FundingPendingTransaction;
import com.a702.finafanbe.core.funding.funding.entity.QFundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.QFundingPendingTransaction;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FundingBatchQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<FundingPendingTransaction> findSuccessFunding() {
        QFundingGroup fundingGroup = QFundingGroup.fundingGroup;
        QFundingPendingTransaction transaction = QFundingPendingTransaction.fundingPendingTransaction;

        return queryFactory
                .selectFrom(transaction)
                .join(fundingGroup).on(transaction.fundingId.eq(fundingGroup.id))
                .where(
                        fundingGroup.fundingExpiryDate.gt(LocalDate.now().minusDays(7)),
                        transaction.deletedAt.isNull()
                )
                .fetch();

    }
}
