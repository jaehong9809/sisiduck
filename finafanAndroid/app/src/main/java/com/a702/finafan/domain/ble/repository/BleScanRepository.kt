package com.a702.finafan.domain.ble.repository

import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

interface BleScanRepository {
    fun addScannedUuid(uuid: UUID)
    fun getScannedUuids(): StateFlow<List<UUID>>
    fun clearScannedUuids()
}
