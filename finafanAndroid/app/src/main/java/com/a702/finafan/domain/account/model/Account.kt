package com.a702.finafan.domain.account.model

import com.a702.finafan.domain.account.model.Bank

data class Account(
    val accountId: Long = 0,
    val accountNo: String = "",
    val bank: Bank = Bank()
)