package com.a702.finafan.domain.savings.usecase

import com.a702.finafan.domain.savings.model.SavingInfo
import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class GetSavingUseCase @Inject constructor(
    private val repository: SavingRepository
) {
    suspend operator fun invoke(savingAccountId: Long): SavingInfo {
        val savingAccount = repository.accountInfo(savingAccountId)
        val transactions = repository.history(savingAccountId)

        return SavingInfo(savingAccount, transactions)
    }
}