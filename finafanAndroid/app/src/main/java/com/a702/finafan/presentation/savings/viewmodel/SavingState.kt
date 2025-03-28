package com.a702.finafan.presentation.savings.viewmodel

import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.domain.savings.model.Transaction

data class SavingState(
    val selectStar: Star = Star(),
    val accountName: String = "",
    val connectAccount: Account = Account(),
    val transaction: Transaction = Transaction()
)