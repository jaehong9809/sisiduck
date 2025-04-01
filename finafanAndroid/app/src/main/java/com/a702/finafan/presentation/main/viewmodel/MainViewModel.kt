package com.a702.finafan.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.domain.main.model.MainRanking
import com.a702.finafan.domain.main.model.MainSaving
import com.a702.finafan.domain.main.model.RankingType
import com.a702.finafan.domain.main.usecase.GetMainRankingUseCase
import com.a702.finafan.domain.main.usecase.GetMainSavingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMainSavingUseCase: GetMainSavingUseCase,
    private val getMainRankingUseCase: GetMainRankingUseCase
): ViewModel() {
    private val _mainSavingState = MutableStateFlow(MainSavingState())
    val mainSavingState: StateFlow<MainSavingState> = _mainSavingState.asStateFlow()

    private val _mainRankingState = MutableStateFlow(MainRankingState())
    val mainRankingState: StateFlow<MainRankingState> = _mainRankingState.asStateFlow()

    fun fetchMainSavings() {
        viewModelScope.launch {
            _mainSavingState.update { it.copy(isLoading = true) }

            val savings: List<MainSaving> = getMainSavingUseCase()
//            val savings: List<MainSaving> = listOf(
//                MainSaving(
//                    savingId = 1L,
//                    starName = "이찬원",
//                    starImageUrl = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/image12.png",
//                    accountNo = "123-456-789",
//                    amount = 500_000L
//                ),
//                MainSaving(
//                    savingId = 2L,
//                    starName = "임영웅",
//                    starImageUrl = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/image12.png",
//                    accountNo = "987-654-321",
//                    amount = 1_200_000L
//                ),
//            )

            _mainSavingState.update {
                it.copy(
                    savings = savings,
                    isLoading = false
                )
            }
        }
    }

    fun fetchMainRanking(type: RankingType) {
        viewModelScope.launch {
            _mainRankingState.update { it.copy(isLoading = true) }

            val rankings: List<MainRanking> = getMainRankingUseCase(type)

            _mainRankingState.update {
                it.copy(
                    rankings = rankings,
                    isLoading = false
                )
            }
        }
    }
}