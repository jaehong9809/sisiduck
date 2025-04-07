package com.a702.finafan.data.savings.repository

import com.a702.finafan.common.data.dto.getOrThrow
import com.a702.finafan.common.data.dto.getOrThrowNull
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
        response.getOrThrow { it.map { dto -> dto.toDomain() } }
    }

    override suspend fun deposit(request: SavingDepositRequest): DataResource<Long> = safeApiCall {
        val map = mutableMapOf<String, RequestBody>().apply {
            put(
                "depositAccountId",
                request.depositAccountId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            )
            put(
                "transactionBalance",
                request.transactionBalance.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())
            )
            put("message", request.message.toRequestBody("text/plain".toMediaTypeOrNull()))
        }

        api.deposit(map, request.imageFile).getOrThrow { it.depositAccountId }
    }

    override suspend fun createSaving(request: SavingCreateRequest): DataResource<Long> = safeApiCall {
        api.createSaving(request).getOrThrow { it.depositAccountId }
    }

    override suspend fun history(savingAccountId: Long): DataResource<List<Transaction>> = safeApiCall {
        api.history(savingAccountId).getOrThrow { it.transactions.map { dto -> dto.toDomain() } }
    }

    override suspend fun accountInfo(savingAccountId: Long): DataResource<SavingAccount> = safeApiCall {
        api.accountInfo(savingAccountId).getOrThrow { it.toDomain() }
    }

    override suspend fun savingAccounts(): DataResource<SavingAccountInfo> = safeApiCall {
        api.savingAccounts().getOrThrow { it.toDomain() }
    }

    override suspend fun withdrawAccount(): DataResource<List<Account>> = safeApiCall {
        api.withdrawAccount().getOrThrow { it.map { dto -> dto.toDomain() } }
    }

    override suspend fun bankList(): DataResource<List<Bank>> = safeApiCall {
        api.bankList().getOrThrow { it.map { dto -> dto.toDomain() } }
    }

    override suspend fun changeSavingName(savingAccountId: Long, name: String): DataResource<String> = safeApiCall {
        val request = mapOf("newName" to name)
        api.updateSavingName(savingAccountId, request).getOrThrow { it.accountName }
    }

    override suspend fun deleteSavingAccount(savingAccountId: Long): DataResource<Boolean> = safeApiCall {
        api.deleteSavingAccount(savingAccountId).getOrThrowNull { true }
    }

    override suspend fun deleteConnectAccount(accountId: Long): DataResource<Boolean> = safeApiCall {
        api.deleteConnectAccount(accountId).getOrThrowNull { true }
    }

    override suspend fun dailyStarRanking(): DataResource<List<Ranking>> = safeApiCall {
        api.dailyStarRanking().getOrThrow { it.map { dto -> dto.toDomain() } }
    }

    override suspend fun weeklyStarRanking(): DataResource<List<Ranking>> = safeApiCall {
        api.weeklyStarRanking().getOrThrow { it.map { dto -> dto.toDomain() } }
    }

    override suspend fun totalStarRanking(): DataResource<List<Ranking>> = safeApiCall {
        api.totalStarRanking().getOrThrow { it.map { dto -> dto.toDomain() } }
    }

    override suspend fun rankingDetail(starId: Long, type: RankingType): DataResource<Ranking> = safeApiCall {
        api.starSavingHistory(starId, type.value).getOrThrow { it.toDomain() }
    }

    override suspend fun selectBanks(bankIds: List<Long>): DataResource<List<Account>> = safeApiCall {
        val map = mapOf("bankIds" to bankIds)
        api.selectBanks(map).getOrThrow { it.map { dto -> dto.toDomain() } }
    }

    override suspend fun selectAccounts(accountNos: List<String>): DataResource<List<Account>> = safeApiCall {
        val map = mapOf("accountNos" to accountNos)
        api.selectAccounts(map).getOrThrow { it.map { dto -> dto.toDomain() } }
    }
}