package com.a702.finafan.domain.savings.model

import com.a702.finafan.domain.account.model.Account

data class SavingAccountInfo(
    val total: Long = 0,
    val accounts: List<SavingAccount> = emptyList()
)

data class SavingAccount(
    val accountId: Long = 0,
    val accountNo: String = "",
    val accountName: String = "",
    val amount: Long = 0,
    val createdDate: String = "",
    val interestRate: String = "",
    val duration: Int = 0,
    val imageUrl: String = "",
    val withdrawalAccount: Account = Account()
)
