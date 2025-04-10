package com.a702.finafan.domain.ble.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.ble.repository.BleRepository

class RegisterBleUuidUseCase(
    private val bleRepository: BleRepository
) {
    suspend operator fun invoke(uuid: String): DataResource<Unit> =
        bleRepository.registerUuid(uuid)
}