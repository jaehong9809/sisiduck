package com.a702.finafan.presentation.funding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.domain.funding.model.FundingCreateForm
import com.a702.finafan.domain.funding.model.MyStar
import com.a702.finafan.domain.funding.usecase.StartFundingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class FundingCreateViewModel @Inject constructor(
    private val startFundingUseCase: StartFundingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FundingCreateState())
    val uiState: StateFlow<FundingCreateState> = _uiState.asStateFlow()

    fun updateSelectedStar(star: MyStar) {
        _uiState.value = _uiState.value.copy(selectedStar = star)
    }

    fun updateFundingTitle(title: String) {
        _uiState.value = _uiState.value.copy(fundingTitle = title)
    }

    fun updateFundingGoal(goal: Long) {
        _uiState.value = _uiState.value.copy(fundingGoal = goal)
    }

    fun updateFundingDescription(desc: String) {
        _uiState.value = _uiState.value.copy(fundingDescription = desc)
    }

    fun updateFundingExpiryDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(fundingExpiryDate = date)
    }

    fun createFunding() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                with(_uiState.value) {
                    startFundingUseCase(
                        FundingCreateForm(
                            title = fundingTitle,
                            starId = selectedStar?.id ?: throw Exception("스타를 선택해주세요."),
                            expiryDate = fundingExpiryDate?: throw Exception("종료 날짜를 선택해주세요."),
                            description = fundingDescription,
                            goalAmount = fundingGoal?: throw Exception("스타를 선택해주세요.")
                        )
                    )
                }

                _uiState.value = _uiState.value.copy(isLoading = false, toastMessage = "펀딩이 생성되었습니다!")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e)
            }
        }
    }
}
