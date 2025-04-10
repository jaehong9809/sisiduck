package com.a702.finafan.domain.ble.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.ble.model.FanDeposit
import com.a702.finafan.domain.ble.repository.BleRepository
import javax.inject.Inject

class GetMatchedFanDepositsUseCase @Inject constructor(
    private val bleRepository: BleRepository
) {
    suspend operator fun invoke(): DataResource<List<FanDeposit>> =
        bleRepository.getMatchedFanDeposits()
}