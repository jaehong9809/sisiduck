package com.a702.finafan.domain.savings.model

data class Account(
    val accountId: Long = 0,
    val accountNo: String = "",
    val bank: Bank = Bank()
)