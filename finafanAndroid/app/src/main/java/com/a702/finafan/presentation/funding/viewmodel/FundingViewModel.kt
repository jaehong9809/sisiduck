package com.a702.finafan.presentation.funding.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.domain.funding.model.FundingFilter
import com.a702.finafan.domain.funding.usecase.GetFundingListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FundingViewModel @Inject constructor(
    private val getFundingListUseCase: GetFundingListUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(FundingState())
    val uiState: StateFlow<FundingState> = _uiState.asStateFlow()

    fun fetchFundings(filter: FundingFilter) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val fundings = getFundingListUseCase(filter)
                Log.d("FundingViewModel - fetchfundings: ", "${fundings}")
                _uiState.value = _uiState.value.copy(
                    fundings = fundings,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e
                )
            }
        }
    }
}
