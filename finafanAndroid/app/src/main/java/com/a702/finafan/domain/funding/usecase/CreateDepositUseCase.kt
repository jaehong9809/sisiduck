package com.a702.finafan.domain.funding.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.domain.funding.repository.FundingRepository
import javax.inject.Inject

class CreateDepositUseCase @Inject constructor(
    private val repository: FundingRepository
) {
    suspend operator fun invoke(fundingId: Long, deposit: Deposit): DataResource<Boolean> {
        return repository.createDeposit(fundingId, deposit)
    }
}