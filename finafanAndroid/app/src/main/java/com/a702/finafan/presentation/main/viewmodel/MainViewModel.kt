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