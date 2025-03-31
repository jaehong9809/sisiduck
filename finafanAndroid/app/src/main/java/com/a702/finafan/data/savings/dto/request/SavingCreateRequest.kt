package com.a702.finafan.data.savings.dto.request

import com.a702.finafan.domain.savings.model.SavingCreate

data class SavingCreateRequest(
    val entertainerId: Long,
    val accountName: String,
    val withdrawalAccountId: Long
)

fun SavingCreate.toData(): SavingCreateRequest {
    return SavingCreateRequest(
        entertainerId = this.entertainerId,
        accountName = this.accountName,
        withdrawalAccountId = this.connectAccount.accountId
    )
}