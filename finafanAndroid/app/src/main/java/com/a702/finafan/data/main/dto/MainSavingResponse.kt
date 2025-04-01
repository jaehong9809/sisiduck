package com.a702.finafan.data.main.dto

import com.a702.finafan.domain.main.model.MainSaving

data class MainSavingResponse (
    val starAccountId: Long,
    val starName: String,
    val starImageUrl: String,
    val accountNo: String,
    val amount: Long
)

fun MainSavingResponse.toDomain(): MainSaving {
    return MainSaving(
        savingId = this.starAccountId,
        starName = this.starName,
        starImageUrl = this.starImageUrl,
        accountNo = this.accountNo,
        amount = this.amount
    )
}