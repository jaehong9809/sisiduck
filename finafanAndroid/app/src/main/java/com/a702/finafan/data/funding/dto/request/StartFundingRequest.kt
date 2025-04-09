package com.a702.finafan.data.funding.dto.request

data class StartFundingRequest(
    val accountNickname: String,
    val entertainerId: Long,
    val fundingExpiryDate: String,
    val description: String,
    val goalAmount: Long
)