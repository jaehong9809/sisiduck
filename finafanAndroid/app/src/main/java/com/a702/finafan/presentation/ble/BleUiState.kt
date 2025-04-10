package com.a702.finafan.presentation.ble

import com.a702.finafan.domain.ble.model.Fan
import com.a702.finafan.domain.ble.model.FanDeposit

data class BleUiState(
    val matchedFans: List<Fan> = emptyList(),
    val fanDeposits: List<FanDeposit> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
