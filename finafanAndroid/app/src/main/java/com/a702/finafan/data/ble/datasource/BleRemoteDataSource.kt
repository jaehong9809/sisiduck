package com.a702.finafan.data.ble.datasource

import java.util.UUID
import javax.inject.Inject

class BleRemoteDataSource @Inject constructor() {

    suspend fun sendDetectedUuid(uuid: UUID): List<UUID> {
        return listOf()
    }
}