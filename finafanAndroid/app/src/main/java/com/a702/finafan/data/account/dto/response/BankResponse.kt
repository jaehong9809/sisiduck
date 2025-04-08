package com.a702.finafan.data.account.dto.response

import com.a702.finafan.domain.account.model.Bank

data class BankResponse(
    val bankId: Long = 0,
    val bankCode: String = "",
    val bankName: String = ""
)

fun BankResponse.toDomain(): Bank {
    return Bank(
        bankId = this.bankId,
        bankCode = this.bankCode,
        bankName = this.bankName
    )
}