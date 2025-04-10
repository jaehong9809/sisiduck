package com.a702.finafan.domain.ble.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.ble.model.Fan
import com.a702.finafan.domain.ble.repository.BleRepository

class MatchFansUseCase(
    private val bleRepository: BleRepository
) {
    suspend operator fun invoke(uuidList: List<String>): DataResource<List<Fan>> =
        bleRepository.matchFans(uuidList)
}