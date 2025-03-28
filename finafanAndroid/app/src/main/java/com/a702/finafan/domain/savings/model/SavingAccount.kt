package com.a702.finafan.domain.savings.model

data class SavingAccount(
    val savingAccountId: Long,
    val accountNo: String,
    val accountName: String,
    val amount: Long,
    val createdDt: String,
    val interestRate: String,
    val duration: Int,
    val imageUrl: String,
    val connectAccount: Account
)
