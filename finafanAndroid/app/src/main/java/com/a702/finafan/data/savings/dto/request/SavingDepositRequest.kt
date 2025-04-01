package com.a702.finafan.data.savings.dto.request

import com.a702.finafan.domain.savings.model.SavingDeposit
import okhttp3.MultipartBody

data class SavingDepositRequest(
    val depositAccountId: Long = 0,
    val message: String = "",
    val transactionBalance: Long = 0,
    val imageFile: MultipartBody.Part?
)

fun SavingDeposit.toData(): SavingDepositRequest {
    return SavingDepositRequest(
        depositAccountId = this.savingAccountId,
        message = this.message,
        transactionBalance = this.amount,
        imageFile = this.imageFile
    )
}