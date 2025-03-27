package com.a702.finafan.presentation.funding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.domain.funding.model.FundingDetail
import com.a702.finafan.domain.funding.repository.FundingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FundingDetailViewModel @Inject constructor(
    private val repository: FundingRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(FundingDetailState())
    val uiState: StateFlow<FundingDetailState> = _uiState.asStateFlow()

    fun setFundingDetail(fundingDetail: FundingDetail) {
        _uiState.value = uiState.value.copy(
            funding = fundingDetail.funding,
            isParticipant = fundingDetail.participated,
            deposits = fundingDetail.depositHistory ?: emptyList()
        )
    }

    fun fetchFundingDetail(fundingId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val fundingDetail = repository.getFunding(fundingId, uiState.value.funding?.title?:"펀딩")
                setFundingDetail(fundingDetail)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e
                )
            }
        }
    }

    fun fetchAllDeposits(fundingId: Long) {
        viewModelScope.launch {
            try {
                val deposits = repository.getAllDeposits(fundingId)
                _uiState.value = _uiState.value.copy(deposits = deposits)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e
                )
            }
        }
    }

    fun fetchMyDeposits(fundingId: Long) {
        viewModelScope.launch {
            try {
                val deposits = repository.getMyDeposits(fundingId)
                _uiState.value = _uiState.value.copy(deposits = deposits)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e
                )
            }
        }
    }
    fun joinFunding(fundingId: Long) {
        viewModelScope.launch {
            try {
                repository.joinFunding(fundingId)
                _uiState.value = _uiState.value.copy(isParticipant = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e
                )
            }
        }
    }
}