package com.a702.finafan.domain.savings.model

import java.time.LocalDateTime

data class Transaction(
    val transactionId: Long = 0,
    val amount: Long = 0,
    val balance: Long = 0,
    val message: String = "",
    val imageUrl: String = "",
    val date: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now()
)
