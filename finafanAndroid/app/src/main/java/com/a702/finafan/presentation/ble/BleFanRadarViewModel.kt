package com.a702.finafan.presentation.ble

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.ble.repository.BleScanRepository
import com.a702.finafan.domain.ble.usecase.GetMatchedFanDepositsUseCase
import com.a702.finafan.domain.ble.usecase.MatchFansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class BleFanRadarViewModel @Inject constructor(
    private val matchFansUseCase: MatchFansUseCase,
    private val getMatchedFanDepositsUseCase: GetMatchedFanDepositsUseCase,
    private val bleScanRepository: BleScanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BleUiState())
    val uiState: StateFlow<BleUiState> = _uiState.asStateFlow()
    val nearbyUuids: StateFlow<List<UUID>> = bleScanRepository.getScannedUuids()

    fun addScannedUuid(uuid: UUID) {
        bleScanRepository.addScannedUuid(uuid)
    }

    fun matchFans(uuids: List<String>) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = matchFansUseCase(uuids)) {
                is DataResource.Success -> _uiState.value = _uiState.value.copy(
                    matchedFans = result.data,
                    isLoading = false
                )
                is DataResource.Error -> updateError(result.throwable.message)
                is DataResource.Loading -> Unit
            }
        }
    }

    fun matchNearbyFans() {
        viewModelScope.launch {
            val uuidStrings = nearbyUuids.value.map { it.toString() }
            println("✅ [matchNearbyFans] uuidStrings = $uuidStrings")
            if (uuidStrings.isNotEmpty()) {
                matchFans(uuidStrings)
            } else {
                println("⚠️ [matchNearbyFans] nearbyUuids가 비어 있음.")
                updateError("주변에 감지된 팬이 없어요.")
            }
        }
    }


    fun fetchMatchedFanDeposits() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = getMatchedFanDepositsUseCase()) {
                is DataResource.Success -> _uiState.value = _uiState.value.copy(
                    fanDeposits = result.data,
                    isLoading = false
                )
                is DataResource.Error -> updateError(result.throwable.message)
                is DataResource.Loading -> TODO()
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    private fun updateError(message: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = message ?: "알 수 없는 오류가 발생했어요.")
    }
}