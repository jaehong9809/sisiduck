package com.a702.finafan.data.savings.dto.request

import com.a702.finafan.domain.savings.model.SavingDeposit
import okhttp3.MultipartBody

data class SavingDepositRequest(
    val savingAccountId: Long,
    val message: String,
    val amount: Long,
    val imageFile: MultipartBody.Part
)

fun SavingDeposit.toData(): SavingDepositRequest {
    return SavingDepositRequest(
        savingAccountId = this.savingAccountId,
        message = this.message,
        amount = this.amount,
        imageFile = this.imageFile
    )
}