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
                        error = e
                    )
                }
            }
        }
    }

    fun fetchSavingInfo(savingAccountId: Long) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true) }

            try {
                val savingInfo = getSavingUseCase(savingAccountId)

                _savingState.update {
                    it.copy(
                        savingInfo = savingInfo,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _savingState.update {
                    it.copy(
                        error = e
                    )
                }
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
                        error = e
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

    fun fetchSavingAccount() {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true) }

            try {
                val savingAccountInfo = getSavingAccountUseCase()

                _savingState.update {
                    it.copy(
                        isLoading = false,
                        savingAccountInfo = savingAccountInfo
                    )
                }
            } catch (e: Exception) {
                _savingState.update {
                    it.copy(
                        error = e
                    )
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
            _savingState.update { it.copy(isLoading = true) }

            try {
                val changeName = updateSavingNameUseCase(savingAccountId, name)

                _savingState.update {
                    it.copy(
                        isLoading = true,
                        accountName = changeName
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

    fun deleteSavingAccount(savingAccountId: Long) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true) }

            try {
                val result = deleteSavingAccountUseCase(savingAccountId)

                _savingState.update {
                    it.copy(
                        isLoading = false,
                        isCancel = result,
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

    fun deleteConnectAccount(accountId: Long) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true) }

            try {
                val result = deleteConnectAccountUseCase(accountId)

                _savingState.update {
                    it.copy(
                        isLoading = false,
                        isCancel = result,
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

    fun fetchStarRanking(type: RankingType) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true) }

            try {
                val rankingList = getStarRankingUseCase(type)

                _savingState.update {
                    it.copy(
                        isLoading = false,
                        rankingList = rankingList
                    )
                }
            } catch (e: Exception) {
                _savingState.update {
                    it.copy(
                        error = e
                    )
                }
            }
        }
    }

    fun fetchStarRankingDetail(starId: Long, type: RankingType) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true) }

            try {
                val ranking = getRankingDetailUseCase(starId, type)

                _savingState.update {
                    it.copy(
                        isLoading = false,
                        ranking = ranking
                    )
                }
            } catch (e: Exception) {
                _savingState.update {
                    it.copy(
                        error = e
                    )
                }
            }
        }
    }

    fun selectBank(bankIds: List<Long>) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true) }

            try {
                val accounts = updateConnectBankUseCase(bankIds)

                _savingState.update {
                    it.copy(
                        isLoading = false,
                        accounts = accounts
                    )
                }
            } catch (e: Exception) {
                _savingState.update {
                    it.copy(
                        error = e
                    )
                }
            }
        }
    }

    fun selectAccount(accountNos: List<String>) {
        viewModelScope.launch {
            _savingState.update { it.copy(isLoading = true) }

            try {
                val accounts = updateConnectAccountUseCase(accountNos)

                _savingState.update {
                    it.copy(
                        isLoading = false,
                        accounts = accounts,
                        isConnect = true
                    )
                }
            } catch (e: Exception) {
                _savingState.update {
                    it.copy(
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