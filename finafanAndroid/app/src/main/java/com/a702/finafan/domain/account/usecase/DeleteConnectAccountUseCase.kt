package com.a702.finafan.domain.account.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.account.repository.AccountRepository
import javax.inject.Inject

class DeleteConnectAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(accountId: Long): DataResource<Boolean> {
        return repository.deleteConnectAccount(accountId)
    }
}