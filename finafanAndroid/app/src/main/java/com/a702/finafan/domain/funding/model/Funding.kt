package com.a702.finafan.domain.funding.model

import java.time.LocalDate

data class Funding (
    val star: Star,
    val id: Long,
    val title: String,
    val accountNo: String,
    val status: String,
    val currentAmount: Long,
    val goalAmount: Long,
    val fundingExpiryDate: LocalDate
)