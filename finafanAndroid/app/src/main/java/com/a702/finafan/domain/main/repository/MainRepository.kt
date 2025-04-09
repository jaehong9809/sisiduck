package com.a702.finafan.domain.main.repository

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.main.model.MainRanking
import com.a702.finafan.domain.main.model.MainSaving

interface MainRepository {
    suspend fun getMainSavings(): DataResource<List<MainSaving>>
    suspend fun getDailyTop3(): DataResource<List<MainRanking>>
    suspend fun getWeeklyTop3(): DataResource<List<MainRanking>>
    suspend fun getTotalTop3(): DataResource<List<MainRanking>>
}