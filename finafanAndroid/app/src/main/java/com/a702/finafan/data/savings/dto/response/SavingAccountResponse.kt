package com.a702.finafan.data.savings.dto.response

import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.SavingAccount

data class SavingAccountResponse(
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

fun SavingAccountResponse.toDomain(): SavingAccount {
    return SavingAccount(
        savingAccountId = this.savingAccountId,
        accountNo = this.accountNo,
        accountName = this.accountName,
        amount = this.amount,
        createdDt = this.createdDt,
        interestRate = this.interestRate,
        duration = this.duration,
        imageUrl = this.imageUrl,
        connectAccount = this.connectAccount
    )
}