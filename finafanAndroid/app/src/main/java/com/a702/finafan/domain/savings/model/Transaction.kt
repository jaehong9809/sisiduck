package com.a702.finafan.domain.savings.model

data class Transaction(
    val amount: Long = 0,
    val balance: Long = 0,
    val message: String = "",
    val imageUrl: String = "",
    val date: String = ""
)
