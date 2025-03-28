package com.a702.finafan.data.ble.repository

import com.a702.finafan.domain.ble.repository.BleScanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject

class BleScanRepositoryImpl @Inject constructor() : BleScanRepository {
    private val scannedUuids = MutableStateFlow<List<UUID>>(emptyList())

    override fun addScannedUuid(uuid: UUID) {
        scannedUuids.update { list ->
            if (uuid !in list) list + uuid else list
        }
    }

    override fun getScannedUuids(): StateFlow<List<UUID>> = scannedUuids

    override fun clearScannedUuids() {
        scannedUuids.value = emptyList()
    }
}
