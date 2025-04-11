package com.a702.finafan.data.funding.dto.request

data class DepositRequest(
    val accountId: Long,
    val balance: Long,
    val content: String? = null
)