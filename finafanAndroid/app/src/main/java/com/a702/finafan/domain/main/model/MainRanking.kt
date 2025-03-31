package com.a702.finafan.domain.main.model

data class MainRanking (
    val starId: Long,
    val starName: String,
    val starImageUrl: String,
    val rank: Int,
    val amount: Long
)