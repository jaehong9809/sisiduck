package com.a702.finafan.data.funding.dto.response

import com.a702.finafan.domain.funding.model.Deposit
import java.time.LocalDateTime

data class DepositResponse(
    val id: Long,
    val name: String,
    val balance: Long,
    val content: String,
    val createdAt: String
)

fun DepositResponse.toDomain(): Deposit {
    return Deposit(
        id = this.id,
        name = this.name,
        balance = this.balance,
        message = this.content,
        createdAt = LocalDateTime.parse(this.createdAt)
    )
}