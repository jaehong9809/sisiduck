package com.a702.finafan.domain.savings.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.main.model.RankingType
import com.a702.finafan.domain.savings.model.Ranking
import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class GetRankingDetailUseCase @Inject constructor(
    private val repository: SavingRepository
) {
    suspend operator fun invoke(starId: Long, type: RankingType): DataResource<Ranking> {
        return repository.rankingDetail(starId, type)
    }
}