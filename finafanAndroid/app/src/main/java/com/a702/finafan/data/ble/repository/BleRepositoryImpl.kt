package com.a702.finafan.data.ble.repository

import com.a702.finafan.data.ble.datasource.BleRemoteDataSource
import com.a702.finafan.domain.ble.repository.BleRepository
import java.util.UUID
import javax.inject.Inject

class BleRepositoryImpl @Inject constructor(
    private val api: BleRemoteDataSource
) : BleRepository {
    override suspend fun detectNearbyUser(uuid: UUID): List<UUID> {
        return api.sendDetectedUuid(uuid)
    }
}