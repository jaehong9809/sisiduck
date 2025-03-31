package com.a702.finafan.domain.savings.model

data class SavingInfo(
    val savingAccount: SavingAccount = SavingAccount(),
    val transactions: List<Transaction> = emptyList()
)