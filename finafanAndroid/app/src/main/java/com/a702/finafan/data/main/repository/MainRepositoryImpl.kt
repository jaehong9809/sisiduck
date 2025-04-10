package com.a702.finafan.data.main.repository

import com.a702.finafan.common.data.dto.getOrThrow
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.common.utils.safeApiCall
import com.a702.finafan.data.main.api.MainApi
import com.a702.finafan.data.main.dto.toDomain
import com.a702.finafan.domain.main.model.MainRanking
import com.a702.finafan.domain.main.model.MainSaving
import com.a702.finafan.domain.main.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: MainApi
): MainRepository {

    override suspend fun getMainSavings(): DataResource<List<MainSaving>> = safeApiCall {
        api.getMainSavingList().getOrThrow { it.map { dto -> dto.toDomain() } }
    }

    override suspend fun getDailyTop3(): DataResource<List<MainRanking>> = safeApiCall {
        api.getDailyTop3().getOrThrow { it.map { dto -> dto.toDomain() } }
    }

    override suspend fun getWeeklyTop3(): DataResource<List<MainRanking>> = safeApiCall {
        api.getWeeklyTop3().getOrThrow { it.map { dto -> dto.toDomain() } }
    }

    override suspend fun getTotalTop3(): DataResource<List<MainRanking>> = safeApiCall {
        api.getTotalTop3().getOrThrow { it.map { dto -> dto.toDomain() } }
    }

}