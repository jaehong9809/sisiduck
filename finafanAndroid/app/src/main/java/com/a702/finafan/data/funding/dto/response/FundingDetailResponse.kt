package com.a702.finafan.data.funding.dto.response

import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.FundingDetail
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
    val imageUrl: String
)

data class AdminUser(
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
                thumbnail = entertainer.imageUrl
            ),
            id = id,
            title = fundingName,
            accountNo = "",
            status = status,
            currentAmount = currentAmount,
            goalAmount = goalAmount,
            fundingExpiryDate = LocalDate.parse(fundingExpiryDate)
        ),
        description = description,
        host = adminUser.adminName,
        hostFundingCount = adminUser.fundingCount,
        hostSuccessCount = adminUser.fundingSuccessCount,
        participated = participated
    )
}