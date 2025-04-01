package com.a702.finafan.domain.main.usecase

import com.a702.finafan.domain.main.model.MainSaving
import com.a702.finafan.domain.main.repository.MainRepository
import javax.inject.Inject

class GetMainSavingUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(): List<MainSaving> {
        val savings: List<MainSaving> = repository.getMainSavings()
        return savings
    }
}