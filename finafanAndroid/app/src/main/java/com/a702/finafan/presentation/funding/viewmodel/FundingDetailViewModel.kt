package com.a702.finafan.presentation.funding.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.domain.funding.model.DepositFilter
import com.a702.finafan.domain.funding.model.FundingDetail
import com.a702.finafan.domain.funding.usecase.GetFundingDepositHistoryUseCase
import com.a702.finafan.domain.funding.usecase.GetFundingDetailUseCase
import com.a702.finafan.domain.funding.usecase.JoinFundingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FundingDetailViewModel @Inject constructor(
    private val getFundingDetailUseCase: GetFundingDetailUseCase,
    private val getFundingDepositHistoryUseCase: GetFundingDepositHistoryUseCase,
    private val joinFundingUseCase: JoinFundingUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(FundingDetailState())
    val uiState: StateFlow<FundingDetailState> = _uiState.asStateFlow()

    fun setFundingDetail(fundingDetail: FundingDetail) {
        _uiState.value = uiState.value.copy(
            funding = fundingDetail.funding,
            isParticipant = fundingDetail.participated,
            deposits = fundingDetail.depositHistory ?: emptyList()
        )
        Log.d("isParticipated: ", "${uiState.value.isParticipant}")
    }

    fun fetchFundingDetail(fundingId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val fundingDetail = getFundingDetailUseCase(fundingId)
                setFundingDetail(fundingDetail)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e
                )
            }
        }
    }

    fun fetchDepositHistory(fundingId: Long, filter: DepositFilter) {
        viewModelScope.launch {
            try {
                val deposits = getFundingDepositHistoryUseCase(fundingId, filter)
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
                joinFundingUseCase(fundingId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e
                )
            }
        }
    }
}