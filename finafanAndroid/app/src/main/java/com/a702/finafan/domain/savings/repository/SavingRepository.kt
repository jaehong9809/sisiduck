package com.a702.finafan.domain.savings.repository

import com.a702.finafan.data.savings.dto.request.SavingDepositRequest
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.SavingAccount
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.domain.savings.model.Transaction

interface SavingRepository {
    suspend fun getStars(): List<Star>

    suspend fun deposit(request: SavingDepositRequest): String

    suspend fun history(savingAccountId: Long): List<Transaction>

    suspend fun accountInfo(savingAccountId: Long): SavingAccount

    suspend fun accountList(): List<SavingAccount>

    suspend fun withdrawAccount(): List<Account>
}