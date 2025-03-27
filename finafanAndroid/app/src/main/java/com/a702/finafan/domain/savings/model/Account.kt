package com.a702.finafan.domain.savings.model

data class Account(
    val accountId: Long,
    val accountNo: String,
    val bank: Bank
)
