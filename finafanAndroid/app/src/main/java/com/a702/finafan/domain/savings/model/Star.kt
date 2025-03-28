package com.a702.finafan.domain.savings.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Star(
    val entertainerId: Long = 0,
    val entertainerName: String = "",
    val birthDate: String = "",
    val entertainerProfileUrl: String = "",
    val fandomName: String = "",
) : Parcelable