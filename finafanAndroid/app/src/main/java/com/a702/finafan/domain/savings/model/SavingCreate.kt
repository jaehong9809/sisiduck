package com.a702.finafan.domain.savings.model

data class SavingCreate(
    val entertainerId: Int,
    val accountName: String,
    val connectAccount: Account
)