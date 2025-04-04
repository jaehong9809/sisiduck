package com.a702.finafan.domain.funding.usecase

import com.a702.finafan.domain.funding.model.MyStar
import com.a702.finafan.domain.funding.repository.FundingRepository
import javax.inject.Inject

class GetMyStarsUseCase @Inject constructor(
    private val repository: FundingRepository
) {
    suspend operator fun invoke(): List<MyStar> {
        val myStars: List<MyStar> = repository.getMyStars()
        return myStars
    }
}