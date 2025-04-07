package com.a702.finafan.domain.savings.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class DeleteConnectAccountUseCase @Inject constructor(
    private val repository: SavingRepository
) {
    suspend operator fun invoke(accountId: Long): DataResource<Boolean> {
        return repository.deleteConnectAccount(accountId)
    }
}