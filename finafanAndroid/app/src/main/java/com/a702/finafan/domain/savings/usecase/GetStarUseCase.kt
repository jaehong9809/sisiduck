package com.a702.finafan.domain.savings.usecase

import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class GetStarUseCase @Inject constructor(
    private val repository: SavingRepository
) {
    suspend operator fun invoke(): List<Star> {
        return repository.getStars()
    }
}