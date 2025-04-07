package com.a702.finafan.presentation.savings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.data.savings.dto.request.SavingCreateRequest
import com.a702.finafan.data.savings.dto.request.SavingDepositRequest
import com.a702.finafan.domain.main.model.RankingType
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.domain.savings.model.Transaction
import com.a702.finafan.domain.savings.usecase.CreateSavingUseCase
import com.a702.finafan.domain.savings.usecase.DeleteConnectAccountUseCase
import com.a702.finafan.domain.savings.usecase.DeleteSavingAccountUseCase
import com.a702.finafan.domain.savings.usecase.DepositUseCase
import com.a702.finafan.domain.savings.usecase.GetBankUseCase
import com.a702.finafan.domain.savings.usecase.GetRankingDetailUseCase
import com.a702.finafan.domain.savings.usecase.GetSavingAccountUseCase
import com.a702.finafan.domain.savings.usecase.GetSavingUseCase
import com.a702.finafan.domain.savings.usecase.GetStarRankingUseCase
import com.a702.finafan.domain.savings.usecase.GetStarUseCase
import com.a702.finafan.domain.savings.usecase.GetWithdrawalAccountUseCase
import com.a702.finafan.domain.savings.usecase.UpdateConnectAccountUseCase
import com.a702.finafan.domain.savings.usecase.UpdateConnectBankUseCase
import com.a702.finafan.domain.savings.usecase.UpdateSavingNameUseCase
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
    private val getSavingAccountUseCase: GetSavingAccountUseCase,
    private val getBankUseCase: GetBankUseCase,
    private val updateSavingNameUseCase: UpdateSavingNameUseCase,
    private val deleteSavingAccountUseCase: DeleteSavingAccountUseCase,
    private val deleteConnectAccountUseCase: DeleteConnectAccountUseCase,
    private val getStarRankingUseCase: GetStarRankingUseCase,
    private val getRankingDetailUseCase: GetRankingDetailUseCase,
    private val updateConnectAccountUseCase: UpdateConnectAccountUseCase,
    private val updateConnectBankUseCase: UpdateConnectBankUseCase,
): ViewModel() {

    private val _savingState = MutableStateFlow(SavingState())
    val savingState: StateFlow<SavingState> = _savingState.asStateFlow()

    private val _starState = MutableStateFlow(StarState())
    val starState: StateFlow<StarState> = _starState.asStateFlow()

    fun fetchStars(keyword: String? = null) {
        viewModelScope.launch {
            _starState.update { it.copy(isLoading = true, error = null) }

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
                    _starState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun fetchSavingInfo(savingAccountId: Long) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true, error = null) }

            when (val result = getSavingUseCase(savingAccountId)) {
                is DataResource.Success -> {
                    _savingState.update {
                        it.copy(
                            savingInfo = result.data,
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
                    _savingState.update { it.copy(isLoading = true) }
                }
            }

        }
    }

    fun createSaving(request: SavingCreateRequest) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true, error = null) }

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
                    _savingState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun fetchWithdrawalAccount() {
        viewModelScope.launch {

            _savingState.update { it.copy(isLoading = true, error = null) }

            when (val result = getWithdrawalAccountUseCase()) {
                is DataResource.Success -> {
                    _savingState.update {
                        it.copy(
                            withdrawalAccounts = result.data,
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
                    _savingState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun depositSaving(request: SavingDepositRequest) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true, error = null) }

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
                    _savingState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun fetchSavingAccount() {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true, error = null) }

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
                    _savingState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun fetchBankList() {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true, error = null) }

            when (val result = getBankUseCase()) {
                is DataResource.Success -> {
                    _savingState.update {
                        it.copy(
                            bankList = result.data,
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
                    _savingState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun changeSavingName(savingAccountId: Long, name: String) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true, error = null) }

            when (val result = updateSavingNameUseCase(savingAccountId, name)) {
                is DataResource.Success -> {
                    _savingState.update {
                        it.copy(
                            accountName = result.data,
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
                    _savingState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun deleteSavingAccount(savingAccountId: Long) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true, error = null) }

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
                    _savingState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun deleteConnectAccount(accountId: Long) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true, error = null) }

            when (val result = deleteConnectAccountUseCase(accountId)) {
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
                    _savingState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun fetchStarRanking(type: RankingType) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true, error = null) }

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
                    _savingState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun fetchStarRankingDetail(starId: Long, type: RankingType) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true, error = null) }

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
                    _savingState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun selectBank(bankIds: List<Long>) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true, error = null) }

            when (val result = updateConnectBankUseCase(bankIds)) {
                is DataResource.Success -> {
                    _savingState.update {
                        it.copy(
                            accounts = result.data,
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
                    _savingState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun selectAccount(accountNos: List<String>) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true, error = null) }

            when (val result = updateConnectAccountUseCase(accountNos)) {
                is DataResource.Success -> {
                    _savingState.update {
                        it.copy(
//                            accounts = result.data,
                            isLoading = false,
                            isConnect = true
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
                    _savingState.update { it.copy(isLoading = true) }
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

    fun updateSavingStar(star: Star) {
        _savingState.update { it.copy(selectStar = star) }
    }

    fun updateSavingName(accountName: String) {
        _savingState.update { it.copy(accountName = accountName) }
    }

    fun updateConnectAccount(account: Account) {
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

    fun resetCancelState() {
        _savingState.update { it.copy(isCancel = false) }
    }

    fun resetConnectState() {
        _savingState.update { it.copy(isConnect = false) }
    }

}