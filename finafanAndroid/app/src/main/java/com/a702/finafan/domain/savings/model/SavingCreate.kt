package com.a702.finafan.domain.savings.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SavingCreate(
    val entertainerId: Long = 0,
    val accountName: String = "",
    val connectAccount: Account = Account()
) : Parcelable