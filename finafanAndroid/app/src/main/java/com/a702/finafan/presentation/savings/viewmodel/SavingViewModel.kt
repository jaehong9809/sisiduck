package com.a702.finafan.presentation.savings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.domain.savings.model.Ranking
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.domain.savings.model.Transaction
import com.a702.finafan.domain.savings.usecase.GetSavingUseCase
import com.a702.finafan.domain.savings.usecase.GetStarsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavingViewModel @Inject constructor(
    private val getStarsUseCase: GetStarsUseCase,
    private val getSavingUseCase: GetSavingUseCase,
): ViewModel() {

    private val _savingState = MutableStateFlow(SavingState())
    val savingState: StateFlow<SavingState> = _savingState.asStateFlow()

    private val _starState = MutableStateFlow(StarState())
    val starState: StateFlow<StarState> = _starState.asStateFlow()

    fun fetchStars() {
        viewModelScope.launch {
            _starState.update { it.copy(isLoading = true) }

            try {
                val stars = getStarsUseCase()

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