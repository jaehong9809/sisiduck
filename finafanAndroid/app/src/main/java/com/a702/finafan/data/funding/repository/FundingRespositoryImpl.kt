package com.a702.finafan.data.funding.repository

import com.a702.finafan.data.funding.api.FundingApi
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.repository.FundingRepository
import javax.inject.Inject

class FundingRepositoryImpl @Inject constructor(
    private val api: FundingApi
) : FundingRepository {
    override suspend fun getFunding(): Funding {
        TODO("Not yet implemented")
    }

    override suspend fun getFundingList(): List<Funding> {
        TODO("Not yet implemented")
    }

    override suspend fun joinFunding() {
        TODO("Not yet implemented")
    }
}
