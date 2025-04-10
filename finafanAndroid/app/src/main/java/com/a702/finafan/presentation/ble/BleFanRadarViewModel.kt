package com.a702.finafan.presentation.ble

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.ble.usecase.GetMatchedFanDepositsUseCase
import com.a702.finafan.domain.ble.usecase.MatchFansUseCase
import com.a702.finafan.domain.ble.usecase.RegisterBleUuidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BleFanRadarViewModel @Inject constructor(
    private val registerBleUuidUseCase: RegisterBleUuidUseCase,
    private val matchFansUseCase: MatchFansUseCase,
    private val getMatchedFanDepositsUseCase: GetMatchedFanDepositsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BleUiState())
    val uiState: StateFlow<BleUiState> = _uiState.asStateFlow()

    fun registerUuid(uuid: String) {
        viewModelScope.launch {
            when (val result = registerBleUuidUseCase(uuid)) {
                is DataResource.Success -> Unit
                is DataResource.Error -> updateError(result.throwable.message)
                is DataResource.Loading -> TODO("찾는 중입니다..!!")
            }
        }
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