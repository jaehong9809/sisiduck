package com.a702.finafan.domain.funding.repository

import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.FundingDetail
import com.a702.finafan.domain.funding.model.FundingFilter

interface FundingRepository {
    suspend fun getFunding(fundingId: Long): FundingDetail
    suspend fun getFundingList(filter: FundingFilter): List<Funding>
    suspend fun joinFunding(fundingId: Long): Boolean
    suspend fun getAllDeposits(fundingId: Long): List<Deposit>
    suspend fun getMyDeposits(fundingId: Long): List<Deposit>
}