package com.a702.finafan.domain.funding.usecase

import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.domain.funding.model.DepositFilter
import com.a702.finafan.domain.funding.repository.FundingRepository
import javax.inject.Inject

class GetFundingDepositHistoryUseCase @Inject constructor(
    private val repository: FundingRepository
) {
    suspend operator fun invoke(fundingId: Long, filter: DepositFilter): List<Deposit> {
        val history: List<Deposit> = repository.getDepositHistory(fundingId, filter)
        return history
    }
}