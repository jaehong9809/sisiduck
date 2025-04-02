package com.a702.finafan.domain.savings.repository

import com.a702.finafan.data.savings.dto.request.SavingCreateRequest
import com.a702.finafan.data.savings.dto.request.SavingDepositRequest
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.domain.savings.model.SavingAccount
import com.a702.finafan.domain.savings.model.SavingAccountInfo
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.domain.savings.model.Transaction

interface SavingRepository {
    suspend fun getStars(keyword: String?): List<Star>

    suspend fun deposit(request: SavingDepositRequest): Long

    suspend fun createSaving(request: SavingCreateRequest): Long

    suspend fun history(savingAccountId: Long): List<Transaction>

    suspend fun accountInfo(savingAccountId: Long): SavingAccount

    suspend fun savingAccounts(): SavingAccountInfo

    suspend fun withdrawAccount(): List<Account>

    suspend fun bankList(): List<Bank>

    suspend fun changeSavingName(savingAccountId: Long, name: String): String

    suspend fun deleteSavingAccount(savingAccountId: Long): Boolean

    suspend fun deleteConnectAccount(accountId: Long): Boolean
}