package com.a702.finafan.domain.savings.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val transactionId: Long = 0,
    val amount: Long = 0,
    val balance: Long = 0,
    val message: String = "",
    val imageUrl: String = "",
    val date: String = ""
) : Parcelable
