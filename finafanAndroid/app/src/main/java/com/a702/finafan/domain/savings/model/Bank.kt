package com.a702.finafan.domain.savings.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bank(
    val bankId: Long = 0,
    val bankCode: String = "",
    val bankName: String = ""
) : Parcelable