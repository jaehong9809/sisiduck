package com.a702.finafan.presentation.funding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.funding.model.FundingFilter
import com.a702.finafan.domain.funding.usecase.GetFundingListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FundingViewModel @Inject constructor(
    private val getFundingListUseCase: GetFundingListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FundingState())
    val fundingState: StateFlow<FundingState> = _uiState.asStateFlow()

    fun fetchFundings(filter: FundingFilter) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = getFundingListUseCase(filter)) {
                is DataResource.Success -> {
                    _uiState.update {
                        it.copy(
                            fundings = result.data,
                            isLoading = false
                        )
                    }
                }

                is DataResource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable
                        )
                    }
                }

                is DataResource.Loading -> {
                    _uiState.update {
                        it.copy(isLoading = true)
                    }
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
