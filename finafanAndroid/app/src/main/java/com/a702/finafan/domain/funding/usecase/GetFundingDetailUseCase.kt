package com.a702.finafan.domain.funding.usecase


import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.funding.model.FundingDetail
import com.a702.finafan.domain.funding.repository.FundingRepository
import javax.inject.Inject

class GetFundingDetailUseCase @Inject constructor(
    private val repository: FundingRepository
) {
    suspend operator fun invoke(fundingId: Long): DataResource<FundingDetail> {
        val funding: DataResource<FundingDetail> = repository.getFunding(fundingId)
        return funding
    }
}

