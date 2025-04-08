package com.a702.finafan.domain.funding.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.funding.repository.FundingRepository
import javax.inject.Inject

class JoinFundingUseCase @Inject constructor(
    private val repository: FundingRepository
) {
    suspend operator fun invoke(fundingId: Long): DataResource<Boolean> {
        return repository.joinFunding(fundingId)
    }
}