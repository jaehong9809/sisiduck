package com.a702.finafan.domain.funding.model

import java.time.LocalDateTime

data class Funding (
    val star: Star,
    val id: Long,
    val title: String,
    val currentAmount: Long,
    val goalAmount: Long,
    val fundingExpiryDate: LocalDateTime
)