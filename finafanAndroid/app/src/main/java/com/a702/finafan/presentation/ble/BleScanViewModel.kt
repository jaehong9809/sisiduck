package com.a702.finafan.presentation.ble

import androidx.lifecycle.ViewModel
import com.a702.finafan.domain.ble.repository.BleScanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BleScanViewModel @Inject constructor(
    private val bleScanRepository: BleScanRepository
) : ViewModel() {

    val nearbyUuids: StateFlow<List<UUID>> = bleScanRepository.getScannedUuids()

    fun onUuidScanned(uuid: UUID) {
        bleScanRepository.addScannedUuid(uuid)
    }

    fun clear() {
        bleScanRepository.clearScannedUuids()
    }
}
