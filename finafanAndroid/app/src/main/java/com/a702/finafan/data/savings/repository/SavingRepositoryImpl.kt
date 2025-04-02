package com.a702.finafan.data.savings.repository

import android.util.Log
import com.a702.finafan.common.domain.ExceptionHandler
import com.a702.finafan.data.savings.api.SavingApi
import com.a702.finafan.data.savings.dto.request.SavingCreateRequest
import com.a702.finafan.data.savings.dto.request.SavingDepositRequest
import com.a702.finafan.data.savings.dto.response.toDomain
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank
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

    override suspend fun getStars(keyword: String?): List<Star> {
        val response = keyword?.run { api.starSearch(this) } ?: api.getStars()

        return if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun deposit(request: SavingDepositRequest): Long {
        return try {
            val map = HashMap<String, RequestBody>()

            map.put(
                "depositAccountId",
                request.depositAccountId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            )

            map.put(
                "transactionBalance",
                request.transactionBalance.toString()
                    .toRequestBody("text/plain".toMediaTypeOrNull())
            )

            map.put("message", request.message.toRequestBody("text/plain".toMediaTypeOrNull()))

            val response = api.deposit(map, request.imageFile)

            if (response.code == "S0000" && response.data != null) {
                response.data.depositAccountId
            } else {
                throw Exception(response.message)
            }
        } catch (e: Exception) {
            Log.d("saving repository", e.toString())
            throw Exception(ExceptionHandler.handle(e))
        }
    }

    override suspend fun createSaving(request: SavingCreateRequest): Long {
        return try {
            val response = api.createSaving(request)

            if (response.code == "S0000" && response.data != null) {
                response.data.depositAccountId
            } else {
                throw Exception(response.message)
            }
        } catch (e: Exception) {
            throw Exception(ExceptionHandler.handle(e))
        }
    }

    override suspend fun history(savingAccountId: Long): List<Transaction> {
        val response = api.history(savingAccountId)

        return if (response.code == "S0000" && response.data != null) {
            response.data.transactions.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun accountInfo(savingAccountId: Long): SavingAccount {
        val response = api.accountInfo(savingAccountId)

        return if (response.code == "S0000" && response.data != null) {
            response.data.toDomain()
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun savingAccounts(): SavingAccountInfo {
        val response = api.savingAccounts()

        return if (response.code == "S0000" && response.data != null) {
            response.data.toDomain()
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun withdrawAccount(): List<Account> {
        val response = api.withdrawAccount()

        return if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun bankList(): List<Bank> {
        val response = api.bankList()

        return if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun changeSavingName(savingAccountId: Long, name: String): String {
        return try {
            val request = HashMap<String, String>()
            request.put("newName", name)

            val response = api.updateSavingName(savingAccountId, request)

            if (response.code == "S0000" && response.data != null) {
                return response.data.accountName
            } else {
                throw Exception(response.message)
            }
        } catch (e: Exception) {
            throw Exception(ExceptionHandler.handle(e))
        }
    }

    override suspend fun deleteSavingAccount(savingAccountId: Long): Boolean {
        return try {
            val response = api.deleteSavingAccount(savingAccountId)

            if (response.code == "S0000" && response.data != null) {
                return true
            } else {
                throw Exception(response.message)
            }
        } catch (e: Exception) {
            throw Exception(ExceptionHandler.handle(e))
        }
    }

}