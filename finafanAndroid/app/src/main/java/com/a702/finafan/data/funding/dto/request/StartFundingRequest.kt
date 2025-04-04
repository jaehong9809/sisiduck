package com.a702.finafan.data.funding.dto.request

import com.a702.finafan.domain.funding.model.FundingCreateForm
import java.time.LocalDate

data class StartFundingRequest(
    val accountNickname: String,
    val entertainerId: Long,
    val fundingExpiryDate: LocalDate,
    val description: String,
    val goalAmount: Long
)

fun FundingCreateForm.toData(): StartFundingRequest {
    return StartFundingRequest(
        accountNickname = this.title,
        entertainerId = this.starId,
        fundingExpiryDate = this.expiryDate,
        description = this.description,
        goalAmount = this.goalAmount
    )
}