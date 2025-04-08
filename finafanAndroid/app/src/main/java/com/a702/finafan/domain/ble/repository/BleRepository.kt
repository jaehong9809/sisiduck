package com.a702.finafan.domain.ble.repository

import java.util.UUID

interface BleRepository {
    suspend fun detectNearbyUser(uuid: UUID): List<UUID>
}