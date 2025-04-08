package com.a702.finafan.presentation.account.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.account.model.Account
import com.a702.finafan.domain.account.model.Bank
import com.a702.finafan.domain.account.usecase.DeleteConnectAccountUseCase
import com.a702.finafan.domain.account.usecase.GetBankUseCase
import com.a702.finafan.domain.account.usecase.GetWithdrawalAccountUseCase
import com.a702.finafan.domain.account.usecase.UpdateConnectAccountUseCase
import com.a702.finafan.domain.account.usecase.UpdateConnectBankUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getWithdrawalAccountUseCase: GetWithdrawalAccountUseCase,
    private val getBankUseCase: GetBankUseCase,
    private val deleteConnectAccountUseCase: DeleteConnectAccountUseCase,
    private val updateConnectAccountUseCase: UpdateConnectAccountUseCase,
    private val updateConnectBankUseCase: UpdateConnectBankUseCase,
): ViewModel() {

    private val _accountState = MutableStateFlow(AccountState())
    val accountState: StateFlow<AccountState> = _accountState.asStateFlow()

    fun fetchWithdrawalAccount() {
        viewModelScope.launch {

            _accountState.update { it.copy(isLoading = true, error = null) }

            when (val result = getWithdrawalAccountUseCase()) {
                is DataResource.Success -> {
                    _accountState.update {
                        it.copy(
                            withdrawalAccounts = result.data,
                            isLoading = false
                        )
                    }
                }
                is DataResource.Error -> {
                    _accountState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                        )
                    }
                }
                is DataResource.Loading -> {
                    _accountState.update {
                        it.copy(
                            isLoading = true,
                            withdrawalAccounts = emptyList()
                        )
                    }
                }
            }
        }
    }

    fun fetchBankList() {
        viewModelScope.launch {
            _accountState.update { it.copy(isLoading = true, error = null) }

            when (val result = getBankUseCase()) {
                is DataResource.Success -> {
                    _accountState.update {
                        it.copy(
                            bankList = result.data,
                            isLoading = false
                        )
                    }
                }
                is DataResource.Error -> {
                    _accountState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                        )
                    }
                }
                is DataResource.Loading -> {
                    _accountState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun deleteConnectAccount(accountId: Long) {
        viewModelScope.launch {
            _accountState.update { it.copy(isLoading = true, error = null) }

            when (val result = deleteConnectAccountUseCase(accountId)) {
                is DataResource.Success -> {
                    _accountState.update {
                        it.copy(
                            isCancel = result.data,
                            isLoading = false
                        )
                    }
                }
                is DataResource.Error -> {
                    _accountState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                        )
                    }
                }
                is DataResource.Loading -> {
                    _accountState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun selectBank(bankIds: List<Long>) {
        viewModelScope.launch {
            _accountState.update { it.copy(isLoading = true, error = null) }

            when (val result = updateConnectBankUseCase(bankIds)) {
                is DataResource.Success -> {
                    _accountState.update {
                        it.copy(
                            accounts = result.data,
                            isLoading = false
                        )
                    }
                }
                is DataResource.Error -> {
                    _accountState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                        )
                    }
                }
                is DataResource.Loading -> {
                    _accountState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun selectAccount(accountNos: List<String>) {
        viewModelScope.launch {
            _accountState.update { it.copy(isLoading = true, error = null) }

            when (val result = updateConnectAccountUseCase(accountNos)) {
                is DataResource.Success -> {
                    _accountState.update {
                        it.copy(
//                            accounts = result.data,
                            isLoading = false,
                            isConnect = true
                        )
                    }
                }
                is DataResource.Error -> {
                    _accountState.update {
                        it.copy(
                            isLoading = false,
                            error = result.throwable,
                        )
                    }
                }
                is DataResource.Loading -> {
                    _accountState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun clearError() {
        _accountState.update {
            it.copy(
                error = null
            )
        }
    }

    fun updateConnectAccount(account: Account) {
        _accountState.update { it.copy(connectAccount = account) }
    }

    fun updateBank(bank: Bank) {
        _accountState.update { it.copy(selectBank = bank) }
    }

    fun updateInputAccountNo(accountNo: String) {
        _accountState.update { it.copy(inputAccountNo = accountNo) }
    }

    fun resetCancelState() {
        _accountState.update { it.copy(isCancel = false) }
    }

    fun resetConnectState() {
        _accountState.update { it.copy(isConnect = false) }
    }

    fun updateSelectAccount(account: Account) {
        _accountState.update { it.copy(selectAccount = account) }
    }

}