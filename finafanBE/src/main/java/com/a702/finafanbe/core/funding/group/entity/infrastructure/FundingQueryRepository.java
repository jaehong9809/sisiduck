package com.a702.finafanbe.core.funding.group.entity.infrastructure;

import com.a702.finafanbe.core.entertainer.entity.QEntertainer;
import com.a702.finafanbe.core.funding.funding.dto.*;
import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;
import com.a702.finafanbe.core.funding.funding.entity.QFundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.QFundingPendingTransaction;
import com.a702.finafanbe.core.funding.group.entity.QGroupUser;
import com.a702.finafanbe.core.funding.group.entity.Role;

import com.a702.finafanbe.core.savings.entity.QSavingsAccount;
import com.a702.finafanbe.core.user.entity.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FundingQueryRepository {

    private final JPAQueryFactory queryFactory;

    QFundingGroup fundingGroup = QFundingGroup.fundingGroup;
    QGroupUser groupUser = QGroupUser.groupUser;
    QEntertainer entertainer = QEntertainer.entertainer;
    QFundingPendingTransaction fundingPendingTransaction = QFundingPendingTransaction.fundingPendingTransaction;
    QUser user = QUser.user;
    QSavingsAccount savingsAccount = QSavingsAccount.savingsAccount;

    public List<GetFundingResponse> findFundings(Long userId, String filter) {
        BooleanBuilder where = new BooleanBuilder();

        if ("participated".equalsIgnoreCase(filter)) {
            where.and(fundingGroup.id.in(
                    JPAExpressions
                            .select(groupUser.fundingGroupId)
                            .from(groupUser)
                            .where(groupUser.userId.eq(userId))
            ));
        } else if ("my".equalsIgnoreCase(filter)) {
            where.and(fundingGroup.id.in(
                    JPAExpressions
                            .select(groupUser.fundingGroupId)
                            .from(groupUser)
                            .where(
                                    groupUser.userId.eq(userId).and(groupUser.role.eq(Role.ADMIN))
                            )
            ));
        } else {
            where.and(fundingGroup.status.eq(FundingStatus.INPROGRESS));
        }

        return queryFactory
                .select(Projections.constructor(
                        GetFundingResponse.class,
                        Projections.constructor(
                                EntertainerResponse.class,
                                entertainer.entertainerId,
                                entertainer.entertainerName,
                                entertainer.entertainerProfileUrl
                        ),
                        fundingGroup.id,
                        fundingGroup.name,
                        filter.equalsIgnoreCase("my") ? savingsAccount.accountNo : Expressions.constant(""),
                        fundingGroup.status,
                        JPAExpressions.select(fundingPendingTransaction.balance.sum().coalesce(0L))
                                .from(fundingPendingTransaction)
                                .where(fundingPendingTransaction.fundingId.eq(fundingGroup.id), fundingPendingTransaction.deletedAt.isNull()),
                        fundingGroup.goalAmount,
                        fundingGroup.createdAt
                ))
                .from(fundingGroup)
                .join(entertainer).on(fundingGroup.entertainerId.eq(entertainer.entertainerId))
                .leftJoin(savingsAccount).on(fundingGroup.accountId.eq(savingsAccount.id))
                .where(where)
                .fetch();
    }


    public GetFundingDetailResponse findFundingDetail(Long userId, Long fundingId) {
        // 모임 가입 여부 확인
        boolean participated = isUserParticipatedInFunding(userId, fundingId);

        // 모임에서의 adminUser의 pk 찾기
        Long adminUserId = queryFactory
                .select(groupUser.userId)
                .from(groupUser)
                .where(groupUser.fundingGroupId.eq(fundingId), groupUser.role.eq(Role.ADMIN))
                .fetchOne();

        String adminName = queryFactory
                .select(user.name)
                .from(user)
                .where(user.userId.eq(adminUserId))
                .fetchFirst();

        Long fundingCount = queryFactory
                .select(groupUser.count())
                .from(groupUser)
                .where(groupUser.userId.eq(adminUserId), groupUser.role.eq(Role.ADMIN))
                .fetchOne();

        Long fundingSuccessCount = queryFactory
                .select(fundingGroup.count())
                .from(fundingGroup)
                .where(
                        fundingGroup.status.eq(FundingStatus.SUCCESS),
                        fundingGroup.id.in(
                                JPAExpressions.select(groupUser.fundingGroupId)
                                        .from(groupUser)
                                        .where(groupUser.userId.eq(adminUserId), groupUser.role.eq(Role.ADMIN))
                        )
                )
                .fetchOne();

        Tuple result = queryFactory
                .select(
                        fundingGroup.name,
                        fundingGroup.description,
                        fundingGroup.status,
                        fundingGroup.goalAmount,
                        fundingGroup.fundingExpiryDate,
                        entertainer.entertainerId,
                        entertainer.entertainerName,
                        entertainer.entertainerProfileUrl,
                        fundingPendingTransaction.balance.sum().coalesce(0L)
                )
                .from(fundingGroup)
                .join(entertainer).on(fundingGroup.entertainerId.eq(entertainer.entertainerId))
                .leftJoin(fundingPendingTransaction).on(fundingPendingTransaction.fundingId.eq(fundingGroup.id))
                .where(fundingGroup.id.eq(fundingId))
                .groupBy(fundingGroup.id, entertainer.entertainerId, entertainer.entertainerName, entertainer.entertainerProfileUrl)
                .fetchOne();
        if (result == null) {
            throw new RuntimeException("펀딩 정보를 찾을 수 없습니다.");
        }

        return new GetFundingDetailResponse(
                participated,
                new EntertainerResponse(
                        result.get(entertainer.entertainerId),
                        result.get(entertainer.entertainerName),
                        result.get(entertainer.entertainerProfileUrl)
                ),
                new GetFundingAdminResponse(
                        adminName,
                        fundingCount != null ? fundingCount.intValue() : 0,
                        fundingSuccessCount != null ? fundingSuccessCount.intValue() : 0
                ),
                result.get(fundingGroup.name),
                result.get(fundingGroup.description),
                result.get(fundingGroup.status),
                result.get(fundingGroup.goalAmount),
                result.get(fundingPendingTransaction.balance.sum().coalesce(0L)),
                result.get(fundingGroup.fundingExpiryDate)
        );

    }

    public List<GetFundingPendingTransactionResponse> getFundingPendingTransaction(Long userId, Long fundingId, String filter) {
        boolean participated = isUserParticipatedInFunding(userId, fundingId);
        if (!participated) {
            return List.of();
        }
        BooleanBuilder where = new BooleanBuilder()
                .and(fundingPendingTransaction.fundingId.eq(fundingId))
                .and(fundingPendingTransaction.deletedAt.isNull());

        if ("my".equalsIgnoreCase(filter)) {
            where.and(fundingPendingTransaction.userId.eq(userId));
        }

        return queryFactory
                .select(new QGetFundingPendingTransactionResponse(
                        fundingPendingTransaction.id,
                        user.name,
                        fundingPendingTransaction.balance,
                        fundingPendingTransaction.content,
                        fundingPendingTransaction.createdAt
                ))
                .from(fundingPendingTransaction)
                .join(user).on(fundingPendingTransaction.userId.eq(user.userId))
                .where(where)
                .orderBy(fundingPendingTransaction.createdAt.desc())
                .fetch();
    }

    public boolean isUserParticipatedInFunding(Long userId, Long fundingGroupId) {
        return queryFactory
                .selectOne()
                .from(groupUser)
                .where(
                        groupUser.fundingGroupId.eq(fundingGroupId),
                        groupUser.userId.eq(userId)
                )
                .fetchFirst() != null;
    }
}