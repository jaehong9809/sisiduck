package com.a702.finafan.data.savings.dto.response

import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank

data class AccountResponse(
    val accountId: Long,
    val accountNo: String,
    val bank: Bank
)

fun AccountResponse.toDomain(): Account {
    return Account(
        accountId = this.accountId,
        accountNo = this.accountNo,
        bank = this.bank
    )
}