package com.a702.finafan.domain.savings.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.main.model.RankingType
import com.a702.finafan.domain.savings.model.Ranking
import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class GetStarRankingUseCase @Inject constructor(
    private val repository: SavingRepository
) {
    suspend operator fun invoke(type: RankingType): DataResource<List<Ranking>> {
        return when (type) {
            RankingType.DAILY -> repository.dailyStarRanking()
            RankingType.WEEKLY -> repository.weeklyStarRanking()
            else -> repository.totalStarRanking()
        }
    }
}