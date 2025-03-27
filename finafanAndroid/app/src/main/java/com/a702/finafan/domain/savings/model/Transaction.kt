package com.a702.finafan.domain.savings.model

data class Transaction(
    val amount: Long,
    val balance: Long,
    val message: String,
    val imageUrl: String,
    val date: String
)
