package com.a702.finafan.data.main.repository

import android.util.Log
import com.a702.finafan.data.main.api.MainApi
import com.a702.finafan.data.main.dto.toDomain
import com.a702.finafan.domain.main.model.MainRanking
import com.a702.finafan.domain.main.model.MainSaving
import com.a702.finafan.domain.main.repository.MainRepository
import javax.inject.Inject
import kotlin.collections.map

class MainRepositoryImpl @Inject constructor(
    private val api: MainApi
): MainRepository {

    override suspend fun getMainSavings(): List<MainSaving> {
        val response = api.getMainSavingList()

        return if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun getDailyTop3(): List<MainRanking> {
        val response = api.getDailyTop3()
        return if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun getWeeklyTop3(): List<MainRanking> {
        val response = api.getWeeklyTop3()
        return if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun getTotalTop3(): List<MainRanking> {
        val response = api.getTotalTop3()
        return if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

}