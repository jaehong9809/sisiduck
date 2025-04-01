package com.a702.finafan.data.savings.dto.response

data class SavingCreateResponse(
    val userId: Long = 0,
    val entertainerId: Long = 0,
    val depositAccountId: Long = 0,
    val withdrawalAccountId: Long = 0
)