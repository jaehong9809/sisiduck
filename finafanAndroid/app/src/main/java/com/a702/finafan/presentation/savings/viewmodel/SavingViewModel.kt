package com.a702.finafan.presentation.savings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.domain.savings.model.Transaction
import com.a702.finafan.domain.savings.repository.SavingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavingViewModel @Inject constructor(
    private val repository: SavingRepository
): ViewModel() {

    private val _savingState = MutableStateFlow(SavingState())
    val savingState: StateFlow<SavingState> = _savingState.asStateFlow()

    private val _starState = MutableStateFlow(StarState())
    val starState: StateFlow<StarState> = _starState.asStateFlow()

    fun fetchStars() {
        viewModelScope.launch {
            _starState.value = _starState.value.copy(isLoading = true)

            try {
                val stars = repository.getStars()

                _starState.value = _starState.value.copy(
                    stars = stars,
                    isLoading = false
                )
            } catch (e: Exception) {
                _starState.value = _starState.value.copy(
                    isLoading = false,
                    error = e
                )
            }
        }
    }

    fun updateSavingStar(star: Star) {
        _savingState.value = _savingState.value.copy(
            selectStar = star
        )
    }

    fun updateSavingName(accountName: String) {
        _savingState.value = _savingState.value.copy(
            accountName = accountName
        )
    }

    fun updateSavingConnectAccount(account: Account) {
        _savingState.value = _savingState.value.copy(
            connectAccount = account
        )
    }

    fun setTransaction(transaction: Transaction) {
        _savingState.value = _savingState.value.copy(
            transaction = transaction
        )
    }

}