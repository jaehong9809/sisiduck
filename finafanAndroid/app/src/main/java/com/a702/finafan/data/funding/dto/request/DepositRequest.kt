package com.a702.finafan.data.funding.dto.request

import com.a702.finafan.domain.funding.model.Deposit

data class DepositRequest(
    val accountId: Long,
    val balance: Long,
    val content: String? = null
)

fun Deposit.toData(): DepositRequest {
    return DepositRequest(
        accountId = accountId,
        balance = balance,
        content = message
    )
}