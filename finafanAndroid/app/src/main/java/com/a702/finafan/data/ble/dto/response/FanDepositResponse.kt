package com.a702.finafan.data.ble.dto.response

import com.a702.finafan.domain.ble.model.FanDeposit

data class FanDepositResponse(
    val fanName: String,
    val message: String,
    val amount: Int,
    val imageUrl: String
)

fun FanDepositResponse.toDomain(): FanDeposit = FanDeposit(
    userName = fanName,
    message = message,
    amount = amount,
    imageUrl = imageUrl
)