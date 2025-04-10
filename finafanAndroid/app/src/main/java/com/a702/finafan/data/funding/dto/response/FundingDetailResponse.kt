package com.a702.finafan.data.funding.dto.response

import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.FundingDetail
import com.a702.finafan.domain.funding.model.FundingStatus
import com.a702.finafan.domain.funding.model.Star
import java.time.LocalDate

data class FundingDetailResponse(
    val participated: Boolean,
    val entertainer: Entertainer,
    val adminUser: AdminUser,
    val fundingName: String,
    val description: String,
    val status: String,
    val goalAmount: Long,
    val currentAmount: Long,
    val fundingExpiryDate: String,
)

data class Entertainer(
    val entertainerId: Long,
    val name: String,
    val imageUrl: String,
    val thumbnailUrl: String
)

data class AdminUser(
    val id: Long,
    val adminName: String,
    val fundingCount: Int,
    val fundingSuccessCount: Int
)

fun FundingDetailResponse.toDomain(id: Long): FundingDetail {
    return FundingDetail(
        funding = Funding(
            star = Star(
                id = entertainer.entertainerId,
                name = entertainer.name,
                image = entertainer.imageUrl,
                index = 0, // TODO: DB에 INDEX 매핑해달라고 해야 함 ...
                thumbnail = entertainer.thumbnailUrl
            ),
            id = id,
            title = fundingName,
            accountNo = "",
            status = FundingStatus.valueOf(status),
            currentAmount = currentAmount,
            goalAmount = goalAmount,
            fundingExpiryDate = LocalDate.parse(fundingExpiryDate)
        ),
        description = description,
        host = adminUser.adminName,
        hostId = adminUser.id,
        hostFundingCount = adminUser.fundingCount,
        hostSuccessCount = adminUser.fundingSuccessCount,
        participated = participated
    )
}