package com.a702.finafan.domain.savings.usecase

import com.a702.finafan.domain.savings.model.Ranking
import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class GetStarRankingUseCase @Inject constructor(
    private val repository: SavingRepository
) {
    suspend operator fun invoke(type: Int): List<Ranking> {
        return when (type) {
            0 -> repository.dailyStarRanking()
            1 -> repository.weeklyStarRanking()
            else -> repository.totalStarRanking()
        }
    }
}