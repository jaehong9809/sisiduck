package com.a702.finafan.domain.account.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.account.model.Account
import com.a702.finafan.domain.account.repository.AccountRepository
import javax.inject.Inject

class GetWithdrawalAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(): DataResource<List<Account>> {
        return repository.withdrawAccount()
    }
}