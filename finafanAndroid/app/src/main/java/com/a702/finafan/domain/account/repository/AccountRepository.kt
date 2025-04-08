package com.a702.finafan.domain.account.repository

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.account.model.Account
import com.a702.finafan.domain.account.model.Bank

interface AccountRepository {

    suspend fun deleteConnectAccount(accountId: Long): DataResource<Boolean>

    suspend fun withdrawAccount(): DataResource<List<Account>>

    suspend fun bankList(): DataResource<List<Bank>>

    suspend fun selectBanks(bankIds: List<Long>): DataResource<List<Account>>

    suspend fun selectAccounts(accountNos: List<String>): DataResource<List<Account>>
}