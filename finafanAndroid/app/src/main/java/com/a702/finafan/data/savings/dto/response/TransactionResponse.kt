package com.a702.finafan.data.savings.dto.response

import com.a702.finafan.domain.savings.model.Transaction

data class TransactionResponse(
    val amount: Long,
    val balance: Long,
    val message: String,
    val imageUrl: String,
    val date: String
)

fun TransactionResponse.toDomain(): Transaction {
    return Transaction(
        amount = this.amount,
        balance = this.balance,
        message = this.message,
        imageUrl = this.imageUrl,
        date = this.date
    )
}