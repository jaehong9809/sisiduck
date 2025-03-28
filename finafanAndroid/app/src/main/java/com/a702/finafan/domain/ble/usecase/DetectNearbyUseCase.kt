package com.a702.finafan.domain.ble.usecase

import com.a702.finafan.domain.ble.repository.BleRepository
import java.util.UUID
import javax.inject.Inject

class DetectNearbyUseCase @Inject constructor(
    private val bleRepository: BleRepository
) {
    suspend operator fun invoke(uuid: UUID): List<UUID> {
        return bleRepository.detectNearbyUser(uuid)
    }
}