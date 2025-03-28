package com.a702.finafan.domain.savings.model

import android.os.Parcelable
import com.a702.finafan.data.savings.dto.request.AccountRequest
import kotlinx.parcelize.Parcelize

@Parcelize
data class Account(
    val accountId: Long = 0,
    val accountNo: String = "",
    val bank: Bank = Bank()
) : Parcelable

fun Account.toData(): AccountRequest {
    return AccountRequest(
        accountNo = this.accountNo,
        bank = this.bank
    )
}