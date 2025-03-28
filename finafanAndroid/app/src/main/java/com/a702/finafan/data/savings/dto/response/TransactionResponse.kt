package com.a702.finafan.data.savings.dto.response

import com.a702.finafan.domain.savings.model.Transaction

data class TransactionResponse(
    val transactionId: Long,
    val amount: Long = 0,
    val balance: Long = 0,
    val message: String = "",
    val imageUrl: String = "",
    val date: String = ""
)

fun TransactionResponse.toDomain(): Transaction {
    return Transaction(
        transactionId = this.transactionId,
        amount = this.amount,
        balance = this.balance,
        message = this.message,
        imageUrl = this.imageUrl,
        date = this.date
    )
}