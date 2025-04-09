package com.a702.finafan.presentation.funding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.funding.model.FundingCreateForm
import com.a702.finafan.domain.funding.model.MyStar
import com.a702.finafan.domain.funding.usecase.GetMyStarsUseCase
import com.a702.finafan.domain.funding.usecase.StartFundingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class FundingCreateViewModel @Inject constructor(
    private val startFundingUseCase: StartFundingUseCase,
    private val getMyStarsUseCase: GetMyStarsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FundingCreateState())
    val uiState: StateFlow<FundingCreateState> = _uiState.asStateFlow()

    fun fetchMyStars() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = getMyStarsUseCase()) {
                is DataResource.Success -> {
                    _uiState.update {
                        it.copy(
                            myStars = result.data,
                            isLoading = false
                        )
                    }
                }
                is DataResource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                            toastMessage = "스타 정보를 불러오는 데 실패했어요: ${result.throwable.message}"
                        )
                    }
                }
                is DataResource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun updateSelectedStar(star: MyStar?) {
        _uiState.value = _uiState.value.copy(selectedStar = star)
    }

    fun updateFundingTitle(title: String) {
        _uiState.value = _uiState.value.copy(fundingTitle = title)
    }

    fun updateFundingGoal(goalString: String) {
        val cleaned = goalString.replace(",", "")
        val goal = cleaned.toLongOrNull()
        _uiState.value = _uiState.value.copy(fundingGoal = goal)
    }

    fun updateFundingDescription(desc: String) {
        _uiState.value = _uiState.value.copy(fundingDescription = desc)
    }

    fun updateFundingExpiryDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(fundingExpiryDate = date)
    }

    fun updateTermAgreed(check: Boolean) {
        _uiState.value = _uiState.value.copy(isTermAgreed = check)
    }


    fun resetFundingCreated() {
        _uiState.update {
            it.copy(isFundingCreated = false)
        }
    }

    fun createFunding() {
        viewModelScope.launch {
            val state = uiState.value

            if (state.selectedStar == null ||
                state.fundingTitle.isBlank() ||
                state.fundingDescription.isBlank() ||
                state.fundingExpiryDate == null ||
                state.fundingGoal == null
            ) {
                _uiState.update {
                    it.copy(toastMessage = "모든 항목을 입력해 주세요.")
                }
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = startFundingUseCase(
                FundingCreateForm(
                    title = state.fundingTitle,
                    description = state.fundingDescription,
                    starId = state.selectedStar.id,
                    expiryDate = state.fundingExpiryDate,
                    goalAmount = state.fundingGoal
                )
            )) {
                is DataResource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            toastMessage = "펀딩이 성공적으로 생성되었습니다!",
                            isFundingCreated = true
                        )
                    }
                }
                is DataResource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                            toastMessage = "펀딩 생성 중 오류 발생: ${result.throwable.message}"
                        )
                    }
                }
                is DataResource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }
}
