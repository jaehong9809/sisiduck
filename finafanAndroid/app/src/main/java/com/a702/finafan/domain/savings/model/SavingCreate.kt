package com.a702.finafan.domain.savings.model

data class SavingCreate(
    val starId: Long = 0,
    val accountName: String = "",
    val connectAccount: Account = Account()
)