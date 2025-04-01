package com.a702.finafan.domain.main.repository

import com.a702.finafan.domain.main.model.MainRanking
import com.a702.finafan.domain.main.model.MainSaving

interface MainRepository {
    suspend fun getMainSavings(): List<MainSaving>
    suspend fun getDailyTop3(): List<MainRanking>
    suspend fun getWeeklyTop3(): List<MainRanking>
    suspend fun getTotalTop3(): List<MainRanking>
}