package com.a702.finafan.presentation.funding.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.domain.funding.model.FundingFilter
import com.a702.finafan.domain.funding.repository.FundingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FundingViewModel @Inject constructor(
    private val repository: FundingRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(FundingState())
    val uiState: StateFlow<FundingState> = _uiState.asStateFlow()

    fun fetchFundings(filter: FundingFilter) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val fundings = repository.getFundingList(FundingFilter.ALL)
                Log.d("FundingViewModel - feachAllfundings: ", "${fundings}")
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
