package com.a702.finafan.domain.savings.model

import com.a702.finafan.domain.account.model.Account

data class SavingCreate(
    val starId: Long = 0,
    val accountName: String = "",
    val connectAccount: Account = Account()
)