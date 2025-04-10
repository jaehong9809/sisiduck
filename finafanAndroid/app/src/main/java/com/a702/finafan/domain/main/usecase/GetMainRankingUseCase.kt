package com.a702.finafan.domain.main.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.main.model.MainRanking
import com.a702.finafan.domain.main.model.RankingType
import com.a702.finafan.domain.main.repository.MainRepository
import javax.inject.Inject

class GetMainRankingUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(type: RankingType): DataResource<List<MainRanking>> {
        return when (type) {
            RankingType.DAILY -> repository.getDailyTop3()
            RankingType.WEEKLY -> repository.getWeeklyTop3()
            RankingType.TOTAL -> repository.getTotalTop3()
        }
    }
}
