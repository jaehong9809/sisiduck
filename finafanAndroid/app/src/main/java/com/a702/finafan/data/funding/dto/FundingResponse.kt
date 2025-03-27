package com.a702.finafan.data.funding.dto

import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.Star
import java.time.LocalDateTime

data class FundingResponse(
    val entertainer: EntertainerResponse,
    val fundingId: Long,
    val fundingName: String,
    val currentAmount: Long,
    val goalAmount: Long,
    val fundingExpiryDate: LocalDateTime
)

fun FundingResponse.toDomain(): Funding {
    return Funding(
        star = this.entertainer.toDomain(),
        id = this.fundingId,
        title = this.fundingName,
        currentAmount = this.currentAmount,
        goalAmount = this.goalAmount,
        fundingExpiryDate = this.fundingExpiryDate
    )
}