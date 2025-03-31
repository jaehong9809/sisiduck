package com.a702.finafan.domain.savings.model

data class SavingAccount(
    val savingAccountId: Long = 0,
    val accountNo: String = "",
    val accountName: String = "",
    val amount: Long = 0,
    val createdDt: String = "",
    val interestRate: String = "",
    val duration: Int = 0,
    val imageUrl: String = "",
    val connectAccount: Account = Account()
)
