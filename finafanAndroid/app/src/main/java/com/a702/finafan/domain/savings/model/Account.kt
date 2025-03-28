package com.a702.finafan.domain.savings.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Account(
    val accountId: Long = 0,
    val accountNo: String = "",
    val bank: Bank = Bank()
) : Parcelable
