package com.a702.finafan.domain.savings.usecase

import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class UpdateSavingNameUseCase @Inject constructor(
    private val repository: SavingRepository
) {
    suspend operator fun invoke(savingAccountId: Long, name: String): String {
        return repository.changeSavingName(savingAccountId, name)
    }
}