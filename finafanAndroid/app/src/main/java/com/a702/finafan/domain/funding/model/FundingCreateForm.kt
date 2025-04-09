package com.a702.finafan.domain.funding.model

import com.a702.finafan.data.funding.dto.request.StartFundingRequest
import java.time.LocalDate

data class FundingCreateForm (
    val title: String,
    val starId: Long,
    val expiryDate: LocalDate,
    val description: String,
    val goalAmount: Long
)
fun FundingCreateForm.toData(): StartFundingRequest {
    return StartFundingRequest(
        accountNickname = this.title,
        entertainerId = this.starId,
        fundingExpiryDate = this.expiryDate.toString(),
        description = this.description,
        goalAmount = this.goalAmount
    )
}