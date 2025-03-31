package com.a702.finafan.data.savings.dto.response

import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.SavingAccount

data class SavingAccountResponse(
    val accountId: Long,
    val accountNo: String,
    val accountName: String,
    val amount: Long,
    val createdDate: String,
    val interestRate: String,
    val duration: Int,
    val imageUrl: String,
    val withdrawalAccount: Account
)

fun SavingAccountResponse.toDomain(): SavingAccount {
    return SavingAccount(
        accountId = this.accountId,
        accountNo = this.accountNo,
        accountName = this.accountName,
        amount = this.amount,
        createdDate = this.createdDate,
        interestRate = this.interestRate,
        duration = this.duration,
        imageUrl = this.imageUrl,
        withdrawalAccount = this.withdrawalAccount
    )
}