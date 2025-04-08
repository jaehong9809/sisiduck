package com.a702.finafan.domain.savings.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.data.savings.dto.request.SavingCreateRequest
import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class CreateSavingUseCase @Inject constructor(
    private val repository: SavingRepository
) {
    suspend operator fun invoke(request: SavingCreateRequest): DataResource<Long> {
        return repository.createSaving(request)
    }
}