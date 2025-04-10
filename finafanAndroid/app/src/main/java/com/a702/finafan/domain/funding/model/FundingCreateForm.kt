package com.a702.finafan.domain.funding.model

import java.time.LocalDate

data class FundingCreateForm (
    val title: String,
    val starId: Long,
    val expiryDate: LocalDate,
    val description: String,
    val goalAmount: Long
)