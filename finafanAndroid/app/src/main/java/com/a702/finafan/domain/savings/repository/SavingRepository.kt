package com.a702.finafan.domain.savings.repository

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.data.savings.dto.request.SavingCreateRequest
import com.a702.finafan.data.savings.dto.request.SavingDepositRequest
import com.a702.finafan.domain.main.model.RankingType
import com.a702.finafan.domain.savings.model.Ranking
import com.a702.finafan.domain.savings.model.SavingAccount
import com.a702.finafan.domain.savings.model.SavingAccountInfo
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.domain.savings.model.Transaction

interface SavingRepository {
    suspend fun getStars(keyword: String?): DataResource<List<Star>>

    suspend fun deposit(request: SavingDepositRequest): DataResource<Long>

    suspend fun createSaving(request: SavingCreateRequest): DataResource<Long>

    suspend fun history(savingAccountId: Long): DataResource<List<Transaction>>

    suspend fun accountInfo(savingAccountId: Long): DataResource<SavingAccount>

    suspend fun savingAccounts(): DataResource<SavingAccountInfo>

    suspend fun changeSavingName(savingAccountId: Long, name: String): DataResource<String>

    suspend fun deleteSavingAccount(savingAccountId: Long): DataResource<Boolean>

    suspend fun dailyStarRanking(): DataResource<List<Ranking>>

    suspend fun weeklyStarRanking(): DataResource<List<Ranking>>

    suspend fun totalStarRanking(): DataResource<List<Ranking>>

    suspend fun rankingDetail(starId: Long, type: RankingType): DataResource<Ranking>
}
