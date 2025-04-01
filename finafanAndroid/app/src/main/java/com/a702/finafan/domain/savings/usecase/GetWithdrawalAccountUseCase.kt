package com.a702.finafan.domain.savings.usecase

import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class GetWithdrawalAccountUseCase @Inject constructor(
    private val repository: SavingRepository
) {
    suspend operator fun invoke(): List<Account> {
        return repository.withdrawAccount()
    }
}