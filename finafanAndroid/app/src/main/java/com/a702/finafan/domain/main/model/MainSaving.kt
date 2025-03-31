package com.a702.finafan.domain.main.model

data class MainSaving (
    val savingId: Long,
    val starName: String,
    val starImageUrl: String,
    val accountNo: String,
    val amount: Long
)