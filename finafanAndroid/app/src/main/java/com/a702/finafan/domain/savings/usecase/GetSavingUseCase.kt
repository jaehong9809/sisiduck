package com.a702.finafan.domain.savings.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.savings.model.SavingInfo
import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class GetSavingUseCase @Inject constructor(
    private val repository: SavingRepository
) {
    suspend operator fun invoke(savingAccountId: Long): DataResource<SavingInfo> {
        val accountResult = repository.accountInfo(savingAccountId)
        if (accountResult !is DataResource.Success) {
            return (accountResult as? DataResource.Error)?.let {
                DataResource.error(it.throwable)
            } ?: DataResource.error(Exception("Unknown error occurred"))
        }

        val historyResult = repository.history(savingAccountId)
        if (historyResult !is DataResource.Success) {
            return (historyResult as? DataResource.Error)?.let {
                DataResource.error(it.throwable)
            } ?: DataResource.error(Exception("Unknown error occurred"))
        }

        return DataResource.success(
            SavingInfo(
                savingAccount = accountResult.data,
                transactions = historyResult.data
            )
        )
    }

}