package com.a702.finafan.domain.savings.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.savings.model.SavingAccountInfo
import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class GetSavingAccountUseCase @Inject constructor(
    private val repository: SavingRepository
) {
    suspend operator fun invoke(): DataResource<SavingAccountInfo> {
        return repository.savingAccounts()
    }
}