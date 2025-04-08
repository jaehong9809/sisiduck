package com.a702.finafan.data.account.repository

import com.a702.finafan.common.data.dto.getOrThrow
import com.a702.finafan.common.data.dto.getOrThrowNull
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.common.utils.safeApiCall
import com.a702.finafan.data.account.api.AccountApi
import com.a702.finafan.data.account.dto.response.toDomain
import com.a702.finafan.data.savings.dto.request.AccountNosRequest
import com.a702.finafan.data.savings.dto.request.BankIdsRequest
import com.a702.finafan.domain.account.model.Account
import com.a702.finafan.domain.account.model.Bank
import com.a702.finafan.domain.account.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApi
): AccountRepository {

    override suspend fun withdrawAccount(): DataResource<List<Account>> = safeApiCall {
        api.withdrawAccount().getOrThrow { it.map { dto -> dto.toDomain() } }
    }

    override suspend fun bankList(): DataResource<List<Bank>> = safeApiCall {
        api.bankList().getOrThrow { it.map { dto -> dto.toDomain() } }
    }

    override suspend fun deleteConnectAccount(accountId: Long): DataResource<Boolean> = safeApiCall {
        api.deleteConnectAccount(accountId).getOrThrowNull { true }
    }

    override suspend fun selectBanks(bankIds: List<Long>): DataResource<List<Account>> = safeApiCall {
        val request = BankIdsRequest(bankIds)
        api.selectBanks(request).getOrThrow { it.map { dto -> dto.toDomain() } }
    }

    override suspend fun selectAccounts(accountNos: List<String>): DataResource<List<Account>> = safeApiCall {
        val request = AccountNosRequest(accountNos)
        api.selectAccounts(request).getOrThrow { it.map { dto -> dto.toDomain() } }
    }
}