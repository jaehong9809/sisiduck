package com.a702.finafan.domain.funding.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.FundingFilter
import com.a702.finafan.domain.funding.repository.FundingRepository
import javax.inject.Inject

class GetFundingListUseCase @Inject constructor(
    private val repository: FundingRepository
) {
    suspend operator fun invoke(filter: FundingFilter): DataResource<List<Funding>> {
        val fundings: DataResource<List<Funding>> = repository.getFundingList(filter)
        return fundings
    }
}