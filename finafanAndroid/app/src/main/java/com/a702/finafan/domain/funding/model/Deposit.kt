package com.a702.finafan.domain.funding.model

import java.time.LocalDateTime

data class Deposit (
    val id: Long,
    val name: String,
    val balance: Long,
    val message: String,
    val createdAt: LocalDateTime
)