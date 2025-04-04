package com.a702.finafan.domain.funding.repository

import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.domain.funding.model.DepositFilter
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.FundingCreateForm
import com.a702.finafan.domain.funding.model.FundingDetail
import com.a702.finafan.domain.funding.model.FundingFilter
import com.a702.finafan.domain.funding.model.MyStar

interface FundingRepository {
    suspend fun getFunding(fundingId: Long): FundingDetail
    suspend fun getFundingList(filter: FundingFilter): List<Funding>
    suspend fun joinFunding(fundingId: Long): Boolean
    suspend fun getDepositHistory(fundingId: Long, filter: DepositFilter): List<Deposit>

    suspend fun startFunding(form: FundingCreateForm): Boolean

    suspend fun getMyStars(): List<MyStar>
}