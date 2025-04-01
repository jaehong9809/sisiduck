package com.a702.finafan.data.savings.dto.response

import com.a702.finafan.domain.savings.model.Transaction

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
    val date: String = ""
)

fun TransactionResponse.toDomain(): Transaction {
    return Transaction(
        transactionId = this.transactionId,
        amount = this.transactionAfterBalance,
        balance = this.transactionBalance,
        message = this.transactionMemo,
        imageUrl = this.imageUrl,
        date = this.date
    )
}