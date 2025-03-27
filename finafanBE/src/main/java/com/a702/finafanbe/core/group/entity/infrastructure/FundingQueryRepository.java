package com.a702.finafanbe.core.group.entity.infrastructure;

import com.a702.finafanbe.core.entertainer.entity.QEntertainer;
import com.a702.finafanbe.core.group.entity.FundingStatus;
import com.a702.finafanbe.core.group.entity.QFundingGroup;
import com.a702.finafanbe.core.group.entity.QGroupUser;
import com.a702.finafanbe.core.group.entity.Role;
import com.a702.finafanbe.core.savings.dto.fundingDto.EntertainerResponse;
import com.a702.finafanbe.core.savings.dto.fundingDto.GetFundingAdminResponse;
import com.a702.finafanbe.core.savings.dto.fundingDto.GetFundingDetailResponse;
import com.a702.finafanbe.core.savings.dto.fundingDto.GetFundingResponse;
import com.a702.finafanbe.core.savings.entity.QFundingApplication;
import com.querydsl.core.BooleanBuilder;
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

    QFundingGroup fg = QFundingGroup.fundingGroup;
    QGroupUser gu = QGroupUser.groupUser;
    QEntertainer e = QEntertainer.entertainer;
    QFundingApplication fa = QFundingApplication.fundingApplication;

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
                        fg.name,
                        JPAExpressions.select(fa.balance.sum())
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


    public GetFundingDetailResponse findFundingDetail(Long groupId) {
        // ADMIN userId 먼저 조회
        Long adminUserId = queryFactory
                .select(gu.userId)
                .from(gu)
                .where(gu.fundingGroupId.eq(groupId), gu.role.eq(Role.ADMIN))
                .fetchFirst();

        return queryFactory
                .select(Projections.constructor(
                        GetFundingDetailResponse.class,
                        Projections.constructor(
                                EntertainerResponse.class,
                                e.entertainerId,
                                e.entertainerName,
                                e.entertainerProfileUrl
                        ),
                        Projections.constructor(
                                GetFundingAdminResponse.class,
                                gu.userId.stringValue(), // adminName (임시)
                                fg.description,
                                JPAExpressions.select(gu.count())
                                        .from(gu)
                                        .where(gu.userId.eq(adminUserId)
                                                        .and(gu.role.eq(Role.ADMIN))
                                        ),
                                // fundingSuccessCount
                                JPAExpressions.select(fg.count())
                                        .from(fg)
                                        .where(fg.status.eq(FundingStatus.FINISHED)
                                                .and(fg.id.in(
                                                                JPAExpressions.select(gu.fundingGroupId)
                                                                        .from(gu)
                                                                        .where(
                                                                                gu.userId.eq(adminUserId)
                                                                                        .and(gu.role.eq(Role.ADMIN))
                                                                        )
                                                        ))
                                        )
                        ),
                        fg.goalAmount,
                        JPAExpressions.select(fa.balance.sum())
                                .from(fa)
                                .where(fa.fundingGroupId.eq(fg.id), fa.deletedAt.isNull()),
                        fg.fundingExpiryDate,
                        Expressions.nullExpression(List.class) // application은 나중에 set
                ))
                .from(fg)
                .join(e).on(fg.entertainerId.eq(e.entertainerId))
                .join(gu).on(gu.fundingGroupId.eq(fg.id).and(gu.role.eq(Role.ADMIN)))
                .where(fg.id.eq(groupId))
                .fetchOne();
    }

}