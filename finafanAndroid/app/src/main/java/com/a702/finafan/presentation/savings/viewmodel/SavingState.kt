package com.a702.finafan.presentation.savings.viewmodel

import com.a702.finafan.common.presentation.BaseState
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.domain.savings.model.Ranking
import com.a702.finafan.domain.savings.model.SavingAccountInfo
import com.a702.finafan.domain.savings.model.SavingInfo
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.domain.savings.model.Transaction

data class SavingState(
    val selectStar: Star = Star(),
    val accountName: String = "",
    val connectAccount: Account = Account(),
    val transaction: Transaction = Transaction(),
    val selectBank: Bank = Bank(),
    val inputAccountNo: String = "",
    val savingInfo: SavingInfo = SavingInfo(),
    val createAccountId: Long = 0,
    val withdrawalAccounts: List<Account> = emptyList(),
    val depositAccountId: Long = 0,
    val savingAccountInfo: SavingAccountInfo = SavingAccountInfo(),
    val bankList: List<Bank> = emptyList(),
    val isCancel: Boolean = false,
    val rankingList: List<Ranking> = emptyList(),
    val ranking: Ranking = Ranking(),
    val accounts: List<Account> = emptyList(),
    override val isLoading: Boolean = true,
    override val error: Throwable? = null,
    override val toastMessage: String? = null
): BaseState