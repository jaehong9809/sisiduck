package com.a702.finafan.domain.ble.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.ble.model.FanDeposit
import com.a702.finafan.domain.ble.repository.BleRepository

class GetMatchedFanDepositsUseCase(
    private val bleRepository: BleRepository
) {
    suspend operator fun invoke(): DataResource<List<FanDeposit>> =
        bleRepository.getMatchedFanDeposits()
}