package com.a702.finafan.data.main.dto

import com.a702.finafan.domain.main.model.MainRanking

data class MainRankingResponse (
    val rank: Int,
    val entertainerId: Long,
    val entertainerName: String,
    val profileUrl: String,
    val totalAmount: Long
)

fun MainRankingResponse.toDomain(): MainRanking {
    return MainRanking(
        rank = this.rank,
        starId = this.entertainerId,
        starName = this.entertainerName,
        starImageUrl = this.profileUrl,
        amount = this.totalAmount
    )
}