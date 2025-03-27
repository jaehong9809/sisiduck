package com.a702.finafan.data.savings.dto.response

import com.a702.finafan.domain.savings.model.Bank

data class BankResponse(
    val bankId: Long,
    val bankCode: String,
    val bankName: String
)

fun BankResponse.toDomain(): Bank {
    return Bank(
        bankId = this.bankId,
        bankCode = this.bankCode,
        bankName = this.bankName
    )
}