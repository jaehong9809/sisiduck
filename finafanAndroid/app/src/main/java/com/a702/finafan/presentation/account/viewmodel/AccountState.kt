package com.a702.finafan.presentation.account.viewmodel

import com.a702.finafan.common.presentation.BaseState
import com.a702.finafan.domain.account.model.Account
import com.a702.finafan.domain.account.model.Bank

data class AccountState(
    val connectAccount: Account = Account(),
    val selectAccount: Account = Account(),
    val selectBank: Bank = Bank(),
    val inputAccountNo: String = "",
    val withdrawalAccounts: List<Account> = emptyList(),
    val bankList: List<Bank> = emptyList(),
    val accounts: List<Account> = emptyList(),
    val isCancel: Boolean = false,
    val isConnect: Boolean = false,
    val dialogShow: Boolean = false,
    override val isLoading: Boolean = true,
    override val error: Throwable? = null,
    override val toastMessage: String? = null
): BaseState