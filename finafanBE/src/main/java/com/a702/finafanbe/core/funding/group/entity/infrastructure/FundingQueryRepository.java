package com.a702.finafanbe.core.funding.group.entity.infrastructure;

import com.a702.finafanbe.core.entertainer.entity.QEntertainer;
import com.a702.finafanbe.core.funding.funding.dto.*;
import com.a702.finafanbe.core.funding.group.entity.FundingStatus;
import com.a702.finafanbe.core.group.entity.QFundingGroup;
import com.a702.finafanbe.core.group.entity.QGroupUser;
import com.a702.finafanbe.core.funding.group.entity.Role;
import com.a702.finafanbe.core.savings.entity.QFundingApplication;
import com.a702.finafanbe.core.user.entity.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FundingQueryRepository {

    private final JPAQueryFactory queryFactory;

    QFundingGroup fg = QFundingGroup.fundingGroup;
    QGroupUser gu = QGroupUser.groupUser;
    QEntertainer e = QEntertainer.entertainer;
    QFundingApplication fa = QFundingApplication.fundingApplication;
    QUser u = QUser.user;

    public List<GetFundingResponse> findFundings(Long userId, String filter) {
        BooleanBuilder where = new BooleanBuilder();
        where.and(fg.status.eq(FundingStatus.INPROGRESS));

        if ("participated".equalsIgnoreCase(filter)) {
            where.and(fg.id.in(
                    JPAExpressions
                            .select(gu.fundingGroupId)
                            .from(gu)
                            .where(gu.userId.eq(userId))
            ));
        } else if ("my".equalsIgnoreCase(filter)) {
            where.and(fg.id.in(
                    JPAExpressions
                            .select(gu.fundingGroupId)
                            .from(gu)
                            .where(
                                    gu.userId.eq(userId).and(gu.role.eq(Role.ADMIN))
                            )
            ));
        }

        return queryFactory
                .select(Projections.constructor(
                        GetFundingResponse.class,
                        Projections.constructor(
                                EntertainerResponse.class,
                                e.entertainerId,
                                e.entertainerName,
                                e.entertainerProfileUrl
                        ),
                        fg.id,
                        fg.name,
                        JPAExpressions.select(fa.balance.sum().coalesce(0L))
                                .from(fa)
                                .where(fa.fundingGroupId.eq(fg.id), fa.deletedAt.isNull()),
                        fg.goalAmount,
                        fg.createdAt
                ))
                .from(fg)
                .join(e).on(fg.entertainerId.eq(e.entertainerId))
                .where(where)
                .fetch();
    }


    public GetFundingDetailResponse findFundingDetail(Long userId, Long fundingId) {
        boolean participated = queryFactory
                .selectOne()
                .from(gu)
                .where(gu.fundingGroupId.eq(groupId), gu.userId.eq(userId))
                .fetchFirst() != null;

        Long adminUserId = queryFactory
                .select(gu.userId)
                .from(gu)
                .where(gu.fundingGroupId.eq(groupId), gu.userId.eq(userId))
                .fetchOne();

        String adminName = queryFactory
                .select(u.name)
                .from(u)
                .where(u.userId.eq(adminUserId))
                .fetchFirst();

        Long fundingCount = queryFactory
                .select(gu.count())
                .from(gu)
                .where(gu.userId.eq(adminUserId), gu.role.eq(Role.ADMIN))
                .fetchOne();

        Long fundingSuccessCount = queryFactory
                .select(fg.count())
                .from(fg)
                .where(
                        fg.status.eq(FundingStatus.SUCCESS),
                        fg.id.in(
                                JPAExpressions.select(gu.fundingGroupId)
                                        .from(gu)
                                        .where(gu.userId.eq(adminUserId), gu.role.eq(Role.ADMIN))
                        )
                )
                .fetchOne();

        Tuple result = queryFactory
                .select(
                        fg.name,
                        fg.goalAmount,
                        fg.fundingExpiryDate,
                        e.entertainerId,
                        e.entertainerName,
                        e.entertainerProfileUrl,
                        fa.balance.sum().coalesce(0L)
                )
                .from(fg)
                .join(e).on(fg.entertainerId.eq(e.entertainerId))
                .leftJoin(fa).on(fa.fundingGroupId.eq(fg.id), fa.deletedAt.isNull())
                .where(fg.id.eq(groupId))
                .groupBy(fg.id, e.entertainerId, e.entertainerName, e.entertainerProfileUrl)
                .fetchOne();

        // 펀딩 참여 리스트
        List<FundingSupportResponse> applications = queryFactory
                .select(
                        Projections.constructor(
                                FundingSupportResponse.class,
                                u.name,
                                fa.balance,
                                fa.createdAt
                        )
                )
                .from(fa)
                .join(u).on(fa.userId.eq(u.userId))
                .where(fa.fundingGroupId.eq(groupId), fa.deletedAt.isNull())
                .fetch();

        return new GetFundingDetailResponse(
                participated,
                new EntertainerResponse(
                        result.get(e.entertainerId),
                        result.get(e.entertainerName),
                        result.get(e.entertainerProfileUrl)
                ),
                new GetFundingAdminResponse(
                        adminName,
                        result.get(fg.name),
                        fundingCount != null ? fundingCount.intValue() : 0,
                        fundingSuccessCount != null ? fundingSuccessCount.intValue() : 0
                ),
                result.get(fg.name),
                result.get(fg.goalAmount),
                result.get(fa.balance.sum()),
                result.get(fg.fundingExpiryDate),
                applications
        );

    }
        // ADMIN userId 먼저 조회
//        Long adminUserId = queryFactory
//                .select(gu.userId)
//                .from(gu)
//                .where(gu.fundingGroupId.eq(groupId), gu.role.eq(Role.ADMIN))
//                .fetchFirst();
//
//        return queryFactory
//                .select(Projections.constructor(
//                        GetFundingDetailResponse.class,
//                        Projections.constructor(
//                                EntertainerResponse.class,
//                                e.entertainerId,
//                                e.entertainerName,
//                                e.entertainerProfileUrl
//                        ),
//                        Projections.constructor(
//                                GetFundingAdminResponse.class,
//                                gu.userId.stringValue(), // adminName (임시)
//                                fg.description,
//                                JPAExpressions.select(gu.count())
//                                        .from(gu)
//                                        .where(gu.userId.eq(adminUserId)
//                                                        .and(gu.role.eq(Role.ADMIN))
//                                        ),
//                                // fundingSuccessCount
//                                JPAExpressions.select(fg.count())
//                                        .from(fg)
//                                        .where(fg.status.eq(FundingStatus.FINISHED)
//                                                .and(fg.id.in(
//                                                                JPAExpressions.select(gu.fundingGroupId)
//                                                                        .from(gu)
//                                                                        .where(
//                                                                                gu.userId.eq(adminUserId)
//                                                                                        .and(gu.role.eq(Role.ADMIN))
//                                                                        )
//                                                        ))
//                                        )
//                        ),
//                        fg.name,
//                        fg.goalAmount,
//                        JPAExpressions.select(fa.balance.sum().coalesce(0L))
//                                .from(fa)
//                                .where(fa.fundingGroupId.eq(fg.id), fa.deletedAt.isNull()),
//                        fg.fundingExpiryDate,
//                        Expressions.nullExpression(List.class) // application은 나중에 set
//                ))
//                .from(fg)
//                .join(e).on(fg.entertainerId.eq(e.entertainerId))
//                .join(gu).on(gu.fundingGroupId.eq(fg.id).and(gu.role.eq(Role.ADMIN)))
//                .where(fg.id.eq(groupId))
//                .fetchOne();
//    }

}