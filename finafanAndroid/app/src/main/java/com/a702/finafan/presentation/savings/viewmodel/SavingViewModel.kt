package com.a702.finafan.presentation.savings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.data.savings.dto.request.SavingCreateRequest
import com.a702.finafan.data.savings.dto.request.SavingDepositRequest
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.domain.savings.model.Ranking
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.domain.savings.model.Transaction
import com.a702.finafan.domain.savings.usecase.CreateSavingUseCase
import com.a702.finafan.domain.savings.usecase.DepositUseCase
import com.a702.finafan.domain.savings.usecase.GetSavingUseCase
import com.a702.finafan.domain.savings.usecase.GetStarUseCase
import com.a702.finafan.domain.savings.usecase.GetWithdrawalAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavingViewModel @Inject constructor(
    private val getStarUseCase: GetStarUseCase,
    private val getSavingUseCase: GetSavingUseCase,
    private val createSavingUseCase: CreateSavingUseCase,
    private val getWithdrawalAccountUseCase: GetWithdrawalAccountUseCase,
    private val depositUseCase: DepositUseCase,
): ViewModel() {

    private val _savingState = MutableStateFlow(SavingState())
    val savingState: StateFlow<SavingState> = _savingState.asStateFlow()

    private val _starState = MutableStateFlow(StarState())
    val starState: StateFlow<StarState> = _starState.asStateFlow()

    fun fetchStars(keyword: String? = null) {
        viewModelScope.launch {
            _starState.update { it.copy(isLoading = true) }

            try {
                val stars = getStarUseCase(keyword)

                _starState.update {
                    it.copy(
                        stars = stars,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _starState.update {
                    it.copy(
                        isLoading = false,
                        error = e
                    )
                }
            }
        }
    }

    fun fetchSavingInfo(savingAccountId: Long) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true) }

            val savingInfo = getSavingUseCase(savingAccountId)

            _savingState.update {
                it.copy(
                    savingInfo = savingInfo,
                    isLoading = false
                )
            }
        }
    }

    fun createSaving(request: SavingCreateRequest) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true) }

            try {
                val accountId = createSavingUseCase(request)

                _savingState.update {
                    it.copy(
                        createAccountId = accountId,
                        isLoading = false,
                    )
                }

            } catch (e: Exception) {
                _savingState.update {
                    it.copy(
                        isLoading = false,
                        error = e
                    )
                }
            }
        }
    }

    fun clearError() {
        _savingState.update {
            it.copy(
                error = null
            )
        }
    }

    fun fetchWithdrawalAccount() {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true) }

            try {
                val withdrawalAccounts = getWithdrawalAccountUseCase()

                _savingState.update {
                    it.copy(
                        isLoading = false,
                        withdrawalAccounts = withdrawalAccounts
                    )
                }
            } catch (e: Exception) {
                _savingState.update {
                    it.copy(
                        isLoading = false,
                        withdrawalAccounts = emptyList()
                    )
                }
            }
        }
    }

    fun depositSaving(request: SavingDepositRequest) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true) }

            try {
                val accountId = depositUseCase(request)

                _savingState.update {
                    it.copy(
                        depositAccountId = accountId,
                        isLoading = false
                    ) }

            } catch (e: Exception) {
                _savingState.update {
                    it.copy(
                        isLoading = false,
                        error = e
                    )
                }
            }
        }
    }

    fun updateSavingStar(star: Star) {
        _savingState.update { it.copy(selectStar = star) }
    }

    fun updateSavingName(accountName: String) {
        _savingState.update { it.copy(accountName = accountName) }
    }

    fun updateSavingConnectAccount(account: Account) {
        _savingState.update { it.copy(connectAccount = account) }
    }

    fun setTransaction(transaction: Transaction) {
        _savingState.update { it.copy(transaction = transaction) }
    }

    fun updateBank(bank: Bank) {
        _savingState.update { it.copy(selectBank = bank) }
    }

    fun updateInputAccountNo(accountNo: String) {
        _savingState.update { it.copy(inputAccountNo = accountNo) }
    }

    fun setRanking(ranking: Ranking) {
        _savingState.update { it.copy(ranking = ranking) }
    }

}