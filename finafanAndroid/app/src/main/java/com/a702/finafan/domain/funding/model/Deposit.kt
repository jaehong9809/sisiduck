package com.a702.finafan.domain.funding.model

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