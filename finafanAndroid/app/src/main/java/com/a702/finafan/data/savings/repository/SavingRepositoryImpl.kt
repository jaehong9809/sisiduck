package com.a702.finafan.data.savings.repository

import com.a702.finafan.common.domain.ExceptionHandler
import com.a702.finafan.data.savings.api.SavingApi
import com.a702.finafan.data.savings.dto.request.SavingCreateRequest
import com.a702.finafan.data.savings.dto.request.SavingDepositRequest
import com.a702.finafan.data.savings.dto.response.toDomain
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.SavingAccount
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.domain.savings.model.Transaction
import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class SavingRepositoryImpl @Inject constructor(
    private val api: SavingApi
): SavingRepository {

    override suspend fun getStars(): List<Star> {
        val response = api.getStars()

        return if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun deposit(request: SavingDepositRequest): String {
        val response = api.deposit(request)

        return if (response.code == "S0000" && response.data != null) {
            response.data
        } else {
            throw Exception(response.message)
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

    override suspend fun accountList(): List<SavingAccount> {
        val response = api.accountList()

        return if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
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

}