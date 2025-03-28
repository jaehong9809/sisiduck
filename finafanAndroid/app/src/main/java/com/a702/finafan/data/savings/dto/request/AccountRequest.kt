package com.a702.finafan.data.savings.dto.request

import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank

data class AccountRequest(
    val accountNo: String = "",
    val bank: Bank = Bank()
)

fun Account.toData(): AccountRequest {
    return AccountRequest(
        accountNo = this.accountNo,
        bank = this.bank
    )
}