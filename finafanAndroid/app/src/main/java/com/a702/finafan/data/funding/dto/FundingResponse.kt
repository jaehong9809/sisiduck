package com.a702.finafan.data.funding.dto

import com.a702.finafan.domain.funding.model.Star

data class FundingResponse(
    val entertainerId: Long,
    val entertainer: Star,
    val fundingName: String,
    val goalAmount: Long,
    val fundingExpiryDate: String
)