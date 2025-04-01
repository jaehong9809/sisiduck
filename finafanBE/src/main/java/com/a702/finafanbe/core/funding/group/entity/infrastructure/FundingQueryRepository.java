package com.a702.finafanbe.core.funding.group.entity.infrastructure;

import com.a702.finafanbe.core.entertainer.entity.QEntertainer;
import com.a702.finafanbe.core.funding.funding.dto.*;
import com.a702.finafanbe.core.funding.funding.entity.FundingStatus;
import com.a702.finafanbe.core.funding.funding.entity.QFundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.QFundingSupport;
import com.a702.finafanbe.core.funding.group.entity.QGroupUser;
import com.a702.finafanbe.core.funding.group.entity.Role;

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

    QFundingGroup fundingGroup = QFundingGroup.fundingGroup;
    QGroupUser groupUser = QGroupUser.groupUser;
    QEntertainer entertainer = QEntertainer.entertainer;
    QFundingSupport fundingSupport = QFundingSupport.fundingSupport;
    QUser user = QUser.user;

    public List<GetFundingResponse> findFundings(Long userId, String filter) {
        BooleanBuilder where = new BooleanBuilder();
        where.and(fundingGroup.deletedAt.isNull());
        where.and(fundingGroup.status.eq(FundingStatus.INPROGRESS));

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
                        JPAExpressions.select(fundingSupport.balance.sum().coalesce(0L))
                                .from(fundingSupport)
                                .where(fundingSupport.fundingGroupId.eq(fundingGroup.id), fundingSupport.deletedAt.isNull()),
                        fundingGroup.goalAmount,
                        fundingGroup.createdAt
                ))
                .from(fundingGroup)
                .join(entertainer).on(fundingGroup.entertainerId.eq(entertainer.entertainerId))
                .where(where)
                .fetch();
    }


    public GetFundingDetailResponse findFundingDetail(Long userId, Long fundingId) {
        boolean participated = queryFactory
                .selectOne()
                .from(groupUser)
                .where(groupUser.fundingGroupId.eq(fundingId), groupUser.userId.eq(userId))
                .fetchFirst() != null;

        Long adminUserId = queryFactory
                .select(groupUser.userId)
                .from(groupUser)
                .where(groupUser.fundingGroupId.eq(fundingId), groupUser.userId.eq(userId))
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
                        fundingGroup.goalAmount,
                        fundingGroup.fundingExpiryDate,
                        entertainer.entertainerId,
                        entertainer.entertainerName,
                        entertainer.entertainerProfileUrl,
                        fundingSupport.balance.sum().coalesce(0L)
                )
                .from(fundingGroup)
                .join(entertainer).on(fundingGroup.entertainerId.eq(entertainer.entertainerId))
                .leftJoin(fundingSupport).on(fundingSupport.fundingGroupId.eq(fundingGroup.id), fundingSupport.deletedAt.isNull())
                .where(fundingGroup.id.eq(fundingId).and(fundingGroup.deletedAt.isNull()))
                .groupBy(fundingGroup.id, entertainer.entertainerId, entertainer.entertainerName, entertainer.entertainerProfileUrl)
                .fetchOne();
        if (result == null) {
            throw new RuntimeException("펀딩 정보를 찾을 수 없습니다.");
        }

        // 펀딩 참여 리스트
        List<FundingSupportResponse> supports = queryFactory
                .select(new QFundingSupportResponse(
                        user.name,
                        fundingSupport.balance,
                        fundingSupport.content,
                        fundingSupport.createdAt
                ))
                .from(fundingSupport)
                .join(user).on(fundingSupport.userId.eq(user.userId))
                .where(fundingSupport.fundingGroupId.eq(fundingId), fundingSupport.deletedAt.isNull())
                .fetch();

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
                result.get(fundingGroup.goalAmount),
                result.get(fundingSupport.balance.sum().coalesce(0L)),
                result.get(fundingGroup.fundingExpiryDate),
                supports
        );

    }

}