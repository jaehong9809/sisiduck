package com.a702.finafan.data.funding.dto

import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.FundingDetail
import com.a702.finafan.domain.funding.model.Star
import java.time.LocalDateTime

data class FundingDetailResponse(
    val participated: Boolean,
    val entertainer: Entertainer,
    val adminUser: AdminUser,
    val fundingName: String,
    val goalAmount: Long,
    val currentAmount: Long,
    val fundingExpiryDate: LocalDateTime,
    val fundingApplication: List<FundingApplication>
)

data class Entertainer(
    val entertainerId: Long,
    val name: String,
    val imageUrl: String
)

data class AdminUser(
    val adminName: String,
    val description: String,
    val fundingCount: Int,
    val fundingSuccessCount: Int
)

data class FundingApplication(
    val name: String,
    val balance: Long,
    val content: String,
    val createdAt: LocalDateTime
)

fun FundingDetailResponse.toDomain(id: Long): FundingDetail {
    return FundingDetail(
        funding = Funding(
            star = Star(
                id = entertainer.entertainerId,
                name = entertainer.name,
                image = entertainer.imageUrl,
                index = 0, // TODO: DB에 INDEX 매핑해달라고 해야 함 ...
                thumbnail = entertainer.imageUrl
            ),
            id = id,
            title = fundingName,
            currentAmount = currentAmount,
            goalAmount = goalAmount,
            fundingExpiryDate = fundingExpiryDate
        ),
        host = adminUser.adminName,
        description = adminUser.description,
        hostFundingCount = adminUser.fundingCount,
        hostSuccessCount = adminUser.fundingSuccessCount,
        depositHistory = fundingApplication.map {
            Deposit(
                name = it.name,
                balance = it.balance,
                message = it.content,
                createdAt = it.createdAt
            )
        },
        participated = participated
    )
}