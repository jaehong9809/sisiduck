package com.a702.finafan.domain.savings.usecase

import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.domain.savings.repository.SavingRepository
import javax.inject.Inject

class GetBankUseCase @Inject constructor(
    private val repository: SavingRepository
) {
    suspend operator fun invoke(): List<Bank> {
        return repository.bankList()
    }
}