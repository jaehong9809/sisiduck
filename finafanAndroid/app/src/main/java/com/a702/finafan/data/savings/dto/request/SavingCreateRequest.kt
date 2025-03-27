package com.a702.finafan.data.savings.dto.request

import com.a702.finafan.domain.savings.model.SavingCreate

data class SavingCreateRequest(
    val entertainerId: Int,
    val accountName: String,
    val connectAccount: Account
)

fun SavingCreate.toData(): SavingCreateRequest {
    return SavingCreateRequest(
        entertainerId = this.entertainerId,
        accountName = this.accountName,
        connectAccount = this.connectAccount
    )
}