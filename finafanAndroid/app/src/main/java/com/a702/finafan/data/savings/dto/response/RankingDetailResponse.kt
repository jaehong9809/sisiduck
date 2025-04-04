package com.a702.finafan.data.savings.dto.response

import com.a702.finafan.domain.savings.model.Ranking

data class RankingDetailResponse (
    val rank: Int = 0,
    val entertainerId: Long = 0,
    val entertainerName: String = "",
    val profileUrl: String = "",
    val totalAmount: Long = 0,
    val transactions: List<TransactionResponse> = emptyList()
)

fun RankingDetailResponse.toDomain(): Ranking {
    return Ranking(
        rank = this.rank,
        starId = this.entertainerId,
        starName = this.entertainerName,
        starImageUrl = this.profileUrl,
        totalAmount = this.totalAmount,
        transactions = this.transactions.map { it.toDomain() }
    )
}