package com.a702.finafan.data.savings.dto.response

data class SavingDepositResponse(
    val depositAccountId: Long = 0,
    val withdrawalAccountId: Long = 0,
    val transactionBalance: Long = 0,
    val transactionUniqueNo: Long = 0,
    val message: String = "",
    val imageUrl: String = ""
)
