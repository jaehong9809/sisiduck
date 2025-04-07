package com.a702.finafan.data.savings.repository

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.common.utils.safeApiCall
import com.a702.finafan.data.savings.api.SavingApi
import com.a702.finafan.data.savings.dto.request.SavingCreateRequest
import com.a702.finafan.data.savings.dto.request.SavingDepositRequest
import com.a702.finafan.data.savings.dto.response.toDomain
import com.a702.finafan.domain.main.model.RankingType
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.domain.savings.model.Ranking
import com.a702.finafan.domain.savings.model.SavingAccount
import com.a702.finafan.domain.savings.model.SavingAccountInfo
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.domain.savings.model.Transaction
import com.a702.finafan.domain.savings.repository.SavingRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class SavingRepositoryImpl @Inject constructor(
    private val api: SavingApi
): SavingRepository {

    override suspend fun getStars(keyword: String?): DataResource<List<Star>> = safeApiCall {
        val response = keyword?.run { api.starSearch(this) } ?: api.getStars()
        if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun deposit(request: SavingDepositRequest): DataResource<Long> = safeApiCall {
        val map = mutableMapOf<String, RequestBody>().apply {
            put("depositAccountId", request.depositAccountId.toString().toRequestBody("text/plain".toMediaTypeOrNull()))
            put("transactionBalance", request.transactionBalance.toString().toRequestBody("text/plain".toMediaTypeOrNull()))
            put("message", request.message.toRequestBody("text/plain".toMediaTypeOrNull()))
        }
        val response = api.deposit(map, request.imageFile)
        if (response.code == "S0000" && response.data != null) {
            response.data.depositAccountId
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun createSaving(request: SavingCreateRequest): DataResource<Long> = safeApiCall {
        val response = api.createSaving(request)
        if (response.code == "S0000" && response.data != null) {
            response.data.depositAccountId
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun history(savingAccountId: Long): DataResource<List<Transaction>> = safeApiCall {
        val response = api.history(savingAccountId)
        if (response.code == "S0000" && response.data != null) {
            response.data.transactions.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun accountInfo(savingAccountId: Long): DataResource<SavingAccount> = safeApiCall {
        val response = api.accountInfo(savingAccountId)
        if (response.code == "S0000" && response.data != null) {
            response.data.toDomain()
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun savingAccounts(): DataResource<SavingAccountInfo> = safeApiCall {
        val response = api.savingAccounts()
        if (response.code == "S0000" && response.data != null) {
            response.data.toDomain()
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun withdrawAccount(): DataResource<List<Account>> = safeApiCall {
        val response = api.withdrawAccount()
        if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun bankList(): DataResource<List<Bank>> = safeApiCall {
        val response = api.bankList()
        if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun changeSavingName(savingAccountId: Long, name: String): DataResource<String> = safeApiCall {
        val request = mapOf("newName" to name)
        val response = api.updateSavingName(savingAccountId, request)
        if (response.code == "S0000" && response.data != null) {
            response.data.accountName
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun deleteSavingAccount(savingAccountId: Long): DataResource<Boolean> = safeApiCall {
        val response = api.deleteSavingAccount(savingAccountId)
        if (response.code == "S0000") {
            true
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun deleteConnectAccount(accountId: Long): DataResource<Boolean> = safeApiCall {
        val response = api.deleteConnectAccount(accountId)
        if (response.code == "S0000") {
            true
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun dailyStarRanking(): DataResource<List<Ranking>> = safeApiCall {
        val response = api.dailyStarRanking()
        if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun weeklyStarRanking(): DataResource<List<Ranking>> = safeApiCall {
        val response = api.weeklyStarRanking()
        if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun totalStarRanking(): DataResource<List<Ranking>> = safeApiCall {
        val response = api.totalStarRanking()
        if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun rankingDetail(starId: Long, type: RankingType): DataResource<Ranking> = safeApiCall {
        val response = api.starSavingHistory(starId, type.value)
        if (response.code == "S0000" && response.data != null) {
            response.data.toDomain()
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun selectBanks(bankIds: List<Long>): DataResource<List<Account>> = safeApiCall {
        val map = mapOf("bankIds" to bankIds)
        val response = api.selectBanks(map)
        if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun selectAccounts(accountNos: List<String>): DataResource<List<Account>> = safeApiCall {
        val map = mapOf("accountNos" to accountNos)
        val response = api.selectAccounts(map)
        if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }
}