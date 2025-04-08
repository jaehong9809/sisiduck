package com.a702.finafan.domain.savings.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.savings.model.SavingAccount
import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class UpdateSavingNameUseCase @Inject constructor(
    private val repository: SavingRepository
) {
    suspend operator fun invoke(savingAccountId: Long, name: String): DataResource<SavingAccount> {
        return repository.changeSavingName(savingAccountId, name)
    }
}