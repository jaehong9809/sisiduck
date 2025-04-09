package com.a702.finafan.data.savings.dto.response

import com.a702.finafan.domain.savings.model.Transaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class TransactionInfo(
    val totalCount: Int = 0,
    val transactions: List<TransactionResponse> = emptyList()
)

data class TransactionResponse(
    val transactionId: Long = 0,
    val transactionAfterBalance: Long = 0,
    val transactionBalance: Long = 0,
    val transactionMemo: String = "",
    val imageUrl: String = "",
    val transactionTime: String = "",
)

fun TransactionResponse.toDomain(): Transaction {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    return Transaction(
        transactionId = this.transactionId,
        amount = this.transactionAfterBalance,
        balance = this.transactionBalance,
        message = this.transactionMemo,
        imageUrl = this.imageUrl,
        date = this.transactionTime,
        createdAt = LocalDateTime.parse(this.transactionTime, formatter)
    )
}