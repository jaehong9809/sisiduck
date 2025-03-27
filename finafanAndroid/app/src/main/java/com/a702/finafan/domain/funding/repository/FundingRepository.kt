package com.a702.finafan.domain.funding.repository

import com.a702.finafan.domain.funding.model.Funding

interface FundingRepository {
    suspend fun getFunding(): Funding
    suspend fun getFundingList(): List<Funding>
    suspend fun joinFunding()
}