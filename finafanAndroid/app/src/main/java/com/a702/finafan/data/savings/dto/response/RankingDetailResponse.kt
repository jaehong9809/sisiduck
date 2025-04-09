package com.a702.finafan.data.savings.dto.response

import com.a702.finafan.domain.savings.model.Ranking
import com.a702.finafan.domain.savings.model.Transaction

data class RankingDetailResponse (
    val rank: Int = 0,
    val entertainerId: Long = 0,
    val entertainerName: String = "",
    val profileUrl: String = "",
    val totalAmount: Long = 0,
    val transactions: List<RankingTransactionResponse> = emptyList()
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

data class RankingTransactionResponse(
    val transactionId: Long = 0,
    val amount: Long = 0,
    val message: String = "",
    val imageUrl: String = "",
    val transactionTime: String = "",
)

fun RankingTransactionResponse.toDomain(): Transaction {
    return Transaction(
        transactionId = this.transactionId,
        balance = this.amount,
        message = this.message,
        imageUrl = this.imageUrl,
        date = this.transactionTime
    )
}