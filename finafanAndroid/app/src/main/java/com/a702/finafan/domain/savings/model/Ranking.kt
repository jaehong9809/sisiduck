package com.a702.finafan.domain.savings.model

data class Ranking(
    val rank: Int = 0,
    val starId: Long = 0,
    val starName: String = "",
    val starImageUrl: String = "",
    val totalAmount: Long = 0,
    val transactions: List<Transaction> = emptyList()
)
