package com.a702.finafan.presentation.funding.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.domain.funding.model.DepositFilter
import com.a702.finafan.domain.funding.usecase.CreateDepositUseCase
import com.a702.finafan.domain.funding.usecase.GetFundingDepositHistoryUseCase
import com.a702.finafan.domain.funding.usecase.GetFundingDetailUseCase
import com.a702.finafan.domain.funding.usecase.JoinFundingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FundingDetailViewModel @Inject constructor(
    private val getFundingDetailUseCase: GetFundingDetailUseCase,
    private val getFundingDepositHistoryUseCase: GetFundingDepositHistoryUseCase,
    private val joinFundingUseCase: JoinFundingUseCase,
    private val createDepositUseCase: CreateDepositUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FundingDetailState())
    val uiState: StateFlow<FundingDetailState> = _uiState.asStateFlow()

    fun fetchFundingDetail(fundingId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = getFundingDetailUseCase(fundingId)) {
                is DataResource.Success -> {
                    val fundingDetail = result.data
                    _uiState.update {
                        it.copy(
                            funding = fundingDetail.funding,
                            isParticipant = fundingDetail.participated,
                            deposits = fundingDetail.depositHistory ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
                is DataResource.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = result.throwable)
                    }
                }
                is DataResource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun fetchDepositHistory(fundingId: Long, filter: DepositFilter) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = getFundingDepositHistoryUseCase(fundingId, filter)) {
                is DataResource.Success -> {
                    Log.d("뷰모델에서 deposit 목록 Success: ", "${result.data}")
                    _uiState.update {
                        it.copy(deposits = result.data, isLoading = false)
                    }
                }
                is DataResource.Error -> {
                    Log.d("뷰모델에서 deposit 목록 Error: ", "$result")
                    _uiState.update {
                        it.copy(isLoading = false, error = result.throwable)
                    }
                }
                is DataResource.Loading -> {
                    Log.d("뷰모델에서 deposit 목록 Loading: ", "$result")
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun joinFunding(fundingId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = joinFundingUseCase(fundingId)) {
                is DataResource.Success -> {
                    _uiState.update {
                        it.copy(isParticipant = true, isLoading = false)
                    }
                }
                is DataResource.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = result.throwable)
                    }
                }
                is DataResource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun createDeposit(fundingId: Long, deposit: Deposit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = createDepositUseCase(fundingId, deposit)) {
                is DataResource.Success -> {
                    _uiState.update {
                        it.copy(isParticipant = true, isLoading = false)
                    }
                }
                is DataResource.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = result.throwable)
                    }
                }
                is DataResource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
