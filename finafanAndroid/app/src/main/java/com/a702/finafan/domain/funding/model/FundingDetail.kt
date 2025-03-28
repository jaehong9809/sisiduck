package com.a702.finafan.domain.funding.model

data class FundingDetail(
    val funding: Funding,
    val host: String,
    val description: String,
    val hostFundingCount: Int,
    val hostSuccessCount: Int,
    val depositHistory: List<Deposit>? = null,
    val participated: Boolean
)