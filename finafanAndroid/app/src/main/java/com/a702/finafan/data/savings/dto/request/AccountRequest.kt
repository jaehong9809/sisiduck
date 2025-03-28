package com.a702.finafan.data.savings.dto.request

import android.os.Parcelable
import com.a702.finafan.domain.savings.model.Bank
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccountRequest(
    val accountNo: String = "",
    val bank: Bank = Bank()
) : Parcelable
