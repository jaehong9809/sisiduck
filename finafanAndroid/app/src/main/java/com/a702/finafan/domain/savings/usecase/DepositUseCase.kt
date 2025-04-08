package com.a702.finafan.domain.savings.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.data.savings.dto.request.SavingDepositRequest
import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class DepositUseCase @Inject constructor(
    private val repository: SavingRepository
) {
    suspend operator fun invoke(request: SavingDepositRequest): DataResource<Long> {
        return repository.deposit(request)
    }
}