package com.a702.finafan.data.funding.dto.response

import com.a702.finafan.domain.funding.model.Deposit
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class DepositResponse(
    val id: Long,
    val name: String,
    val balance: Long,
    val content: String,
    val createdAt: String
)

fun DepositResponse.toDomain(): Deposit {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return Deposit(
        id = this.id,
        name = this.name,
        balance = this.balance,
        message = this.content,
        createdAt = LocalDateTime.parse(createdAt, formatter)
    )
}