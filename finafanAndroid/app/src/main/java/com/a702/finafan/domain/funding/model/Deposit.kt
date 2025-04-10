package com.a702.finafan.domain.funding.model

import com.a702.finafan.data.funding.dto.request.DepositRequest
import java.time.LocalDateTime

data class Deposit (
    val id: Long? = null,
    val fundingId: Long? = null,
    val accountId: Long = 0,
    val name: String,
    val balance: Long,
    val message: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
fun Deposit.toData(): DepositRequest {
    return DepositRequest(
        accountId = accountId,
        balance = balance,
        content = message
    )
}