package com.a702.finafan.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.data.user.local.UserPreferences
import com.a702.finafan.domain.main.model.RankingType
import com.a702.finafan.domain.main.usecase.GetMainRankingUseCase
import com.a702.finafan.domain.main.usecase.GetMainSavingUseCase
import com.a702.finafan.domain.user.model.User
import com.a702.finafan.domain.user.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMainSavingUseCase: GetMainSavingUseCase,
    private val getMainRankingUseCase: GetMainRankingUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val userPreferences: UserPreferences
): ViewModel() {

    val isLoggedIn = userPreferences.userStateFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    private val _userState = MutableStateFlow<DataResource<User>>(DataResource.Loading())
    val userState = _userState.asStateFlow()

    private val _mainSavingState = MutableStateFlow(MainSavingState())
    val mainSavingState: StateFlow<MainSavingState> = _mainSavingState.asStateFlow()

    private val _mainRankingState = MutableStateFlow(MainRankingState())
    val mainRankingState: StateFlow<MainRankingState> = _mainRankingState.asStateFlow()

    fun fetchMainSavings() {
        viewModelScope.launch {
            when (val result = getMainSavingUseCase()) {
                is DataResource.Success -> {
                    _mainSavingState.update {
                        it.copy(
                            savings = result.data,
                            isLoading = false
                        )
                    }
                }
                is DataResource.Error -> {
                    _mainSavingState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                        )
                    }
                }
                is DataResource.Loading -> {
                    _mainSavingState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun fetchMainRanking(type: RankingType) {
        viewModelScope.launch {
            when (val result = getMainRankingUseCase(type)) {
                is DataResource.Success -> {
                    _mainRankingState.update {
                        it.copy(
                            rankings = result.data,
                            isLoading = false
                        )
                    }
                }
                is DataResource.Error -> {
                    _mainRankingState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                        )
                    }
                }
                is DataResource.Loading -> {
                    _mainRankingState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun fetchUserInfo() {
        viewModelScope.launch {
            _userState.value = DataResource.Loading()

            val result = getUserInfoUseCase()
            _userState.value = result
        }
    }
}