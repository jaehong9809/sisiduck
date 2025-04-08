package com.a702.finafan.data.savings.dto.request

import com.a702.finafan.domain.savings.model.SavingCreate

data class SavingCreateRequest(
    val entertainerId: Long,
    val productName: String,
    val withdrawalAccountId: Long
)

fun SavingCreate.toData(): SavingCreateRequest {
    return SavingCreateRequest(
        entertainerId = this.starId,
        productName = this.accountName,
        withdrawalAccountId = this.connectAccount.accountId
    )
}