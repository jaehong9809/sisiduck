package com.a702.finafan.domain.funding.repository

import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.FundingDetail

interface FundingRepository {
    suspend fun getFunding(fundingId: Long, fundingTitle: String): FundingDetail
    suspend fun getAllFundingList(): List<Funding>
    suspend fun getParticipatingFundingList(): List<Funding>
    suspend fun getMyFundingList(): List<Funding>
    suspend fun joinFunding(fundingId: Long)
    suspend fun getAllDeposits(fundingId: Long): List<Deposit>
    suspend fun getMyDeposits(fundingId: Long): List<Deposit>
}