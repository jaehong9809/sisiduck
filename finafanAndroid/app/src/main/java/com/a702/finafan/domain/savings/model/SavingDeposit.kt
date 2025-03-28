package com.a702.finafan.domain.savings.model

import okhttp3.MultipartBody

data class SavingDeposit(
    val savingAccountId: Long = 0,
    val message: String = "",
    val amount: Long = 0,
    val imageFile: MultipartBody.Part
)