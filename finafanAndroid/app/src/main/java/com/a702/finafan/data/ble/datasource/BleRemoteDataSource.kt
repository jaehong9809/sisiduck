package com.a702.finafan.data.ble.datasource

import java.util.UUID

interface BleRemoteDataSource {
    suspend fun sendDetectedUuid(uuid: UUID) : List<UUID>
}