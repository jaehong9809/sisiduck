package com.a702.finafan.domain.savings.model

import okhttp3.MultipartBody

data class SavingDeposit(
    val savingAccountId: Long,
    val message: String,
    val amount: Long,
    val imageFile: MultipartBody.Part
)