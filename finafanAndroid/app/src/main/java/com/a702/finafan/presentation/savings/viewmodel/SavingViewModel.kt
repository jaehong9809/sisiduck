package com.a702.finafan.presentation.savings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.data.savings.dto.request.SavingCreateRequest
import com.a702.finafan.data.savings.dto.request.SavingDepositRequest
import com.a702.finafan.data.user.local.UserPreferences
import com.a702.finafan.domain.main.model.RankingType
import com.a702.finafan.domain.savings.model.SavingAccount
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.domain.savings.model.Transaction
import com.a702.finafan.domain.savings.usecase.CreateSavingUseCase
import com.a702.finafan.domain.savings.usecase.DeleteSavingAccountUseCase
import com.a702.finafan.domain.savings.usecase.DepositUseCase
import com.a702.finafan.domain.savings.usecase.GetRankingDetailUseCase
import com.a702.finafan.domain.savings.usecase.GetSavingAccountUseCase
import com.a702.finafan.domain.savings.usecase.GetSavingUseCase
import com.a702.finafan.domain.savings.usecase.GetStarRankingUseCase
import com.a702.finafan.domain.savings.usecase.GetStarUseCase
import com.a702.finafan.domain.savings.usecase.UpdateSavingNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavingViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val getStarUseCase: GetStarUseCase,
    private val getSavingUseCase: GetSavingUseCase,
    private val createSavingUseCase: CreateSavingUseCase,
    private val depositUseCase: DepositUseCase,
    private val getSavingAccountUseCase: GetSavingAccountUseCase,
    private val updateSavingNameUseCase: UpdateSavingNameUseCase,
    private val deleteSavingAccountUseCase: DeleteSavingAccountUseCase,
    private val getStarRankingUseCase: GetStarRankingUseCase,
    private val getRankingDetailUseCase: GetRankingDetailUseCase,
): ViewModel() {

    val isLoggedIn = userPreferences.userStateFlow
        .map { it.isLoggedIn }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    private val _savingState = MutableStateFlow(SavingState())
    val savingState: StateFlow<SavingState> = _savingState.asStateFlow()

    private val _starState = MutableStateFlow(StarState())
    val starState: StateFlow<StarState> = _starState.asStateFlow()

    fun fetchStars(keyword: String? = null) {
        viewModelScope.launch {
            when (val result = getStarUseCase(keyword)) {
                is DataResource.Success -> {
                    _starState.update {
                        it.copy(
                            stars = result.data,
                            isLoading = false
                        )
                    }
                }
                is DataResource.Error -> {
                    _starState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                        )
                    }
                }
                is DataResource.Loading -> {
                    _starState.update { it.copy(isLoading = true, error = null) }
                }
            }
        }
    }

    fun fetchSavingInfo(savingAccountId: Long) {
        viewModelScope.launch {
            when (val result = getSavingUseCase(savingAccountId)) {
                is DataResource.Success -> {
                    _savingState.update {
                        it.copy(
                            savingAccount = result.data.savingAccount,
                            transactions = result.data.transactions,
                            isLoading = false
                        )
                    }
                }
                is DataResource.Error -> {
                    _savingState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                        )
                    }
                }
                is DataResource.Loading -> {
                    _savingState.update {
                        it.copy(
                            savingAccount = SavingAccount(),
                            transactions = emptyList(),
                            isLoading = true,
                            error = null
                        )
                    }
                }
            }

        }
    }

    fun createSaving(request: SavingCreateRequest) {
        viewModelScope.launch {
            when (val result = createSavingUseCase(request)) {
                is DataResource.Success -> {
                    _savingState.update {
                        it.copy(
                            createAccountId = result.data,
                            isLoading = false
                        )
                    }
                }
                is DataResource.Error -> {
                    _savingState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                        )
                    }
                }
                is DataResource.Loading -> {
                    _savingState.update {
                        it.copy(
                            createAccountId = 0,
                            isLoading = true,
                            error = null
                        )
                    }
                }
            }
        }
    }

    fun depositSaving(request: SavingDepositRequest) {
        viewModelScope.launch {
            when (val result = depositUseCase(request)) {
                is DataResource.Success -> {
                    _savingState.update {
                        it.copy(
                            depositAccountId = result.data,
                            isLoading = false
                        )
                    }
                }
                is DataResource.Error -> {
                    _savingState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                        )
                    }
                }
                is DataResource.Loading -> {
                    _savingState.update {
                        it.copy(
                            depositAccountId = 0,
                            isLoading = true,
                            error = null
                        ) }
                }
            }
        }
    }

    fun fetchSavingAccount() {
        viewModelScope.launch {
            when (val result = getSavingAccountUseCase()) {
                is DataResource.Success -> {
                    _savingState.update {
                        it.copy(
                            savingAccountInfo = result.data,
                            isLoading = false
                        )
                    }
                }
                is DataResource.Error -> {
                    _savingState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                        )
                    }
                }
                is DataResource.Loading -> {
                    _savingState.update { it.copy(isLoading = true, error = null) }
                }
            }
        }
    }

    fun changeSavingName(savingAccountId: Long, name: String) {
        viewModelScope.launch {
            when (val result = updateSavingNameUseCase(savingAccountId, name)) {
                is DataResource.Success -> {
                    _savingState.update {
                        it.copy(
                            savingAccount = result.data,
                            isLoading = false
                        )
                    }
                }

                is DataResource.Error -> {
                    _savingState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                        )
                    }
                }

                is DataResource.Loading -> {
                    _savingState.update { it.copy(isLoading = true, error = null) }
                }
            }
        }
    }

    fun deleteSavingAccount(savingAccountId: Long) {
        viewModelScope.launch {
            when (val result = deleteSavingAccountUseCase(savingAccountId)) {
                is DataResource.Success -> {
                    _savingState.update {
                        it.copy(
                            isCancel = result.data,
                            isLoading = false
                        )
                    }
                }
                is DataResource.Error -> {
                    _savingState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                        )
                    }
                }
                is DataResource.Loading -> {
                    _savingState.update { it.copy(isLoading = true, error = null) }
                }
            }
        }
    }

    fun fetchStarRanking(type: RankingType) {
        viewModelScope.launch {
            when (val result = getStarRankingUseCase(type)) {
                is DataResource.Success -> {
                    _savingState.update {
                        it.copy(
                            rankingList = result.data,
                            isLoading = false
                        )
                    }
                }
                is DataResource.Error -> {
                    _savingState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                        )
                    }
                }
                is DataResource.Loading -> {
                    _savingState.update { it.copy(isLoading = true, error = null) }
                }
            }
        }
    }

    fun fetchStarRankingDetail(starId: Long, type: RankingType) {
        viewModelScope.launch {
            when (val result = getRankingDetailUseCase(starId, type)) {
                is DataResource.Success -> {
                    _savingState.update {
                        it.copy(
                            ranking = result.data,
                            isLoading = false
                        )
                    }
                }
                is DataResource.Error -> {
                    _savingState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                        )
                    }
                }
                is DataResource.Loading -> {
                    _savingState.update { it.copy(isLoading = true, error = null) }
                }
            }
        }
    }

    fun clearError() {
        _savingState.update { it.copy(error = null) }
    }

    fun updateSavingStar(star: Star) {
        _savingState.update { it.copy(selectStar = star) }
    }

    fun updateSavingName(accountName: String) {
        _savingState.update { it.copy(accountName = accountName) }
    }

    fun setTransaction(transaction: Transaction) {
        _savingState.update { it.copy(transaction = transaction) }
    }

    fun resetCancelState() {
        _savingState.update { it.copy(isCancel = false) }
    }

    fun resetDeposit() {
        _savingState.update { it.copy(depositAccountId = 0) }
    }

    fun resetCreate() {
        _savingState.update { it.copy(createAccountId = 0) }
    }

}
