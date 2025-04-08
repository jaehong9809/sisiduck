package com.a702.finafan.domain.savings.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class UpdateConnectBankUseCase @Inject constructor(
    private val repository: SavingRepository
) {
    suspend operator fun invoke(bankIds: List<Long>): DataResource<List<Account>> {
        return repository.selectBanks(bankIds)
    }
}