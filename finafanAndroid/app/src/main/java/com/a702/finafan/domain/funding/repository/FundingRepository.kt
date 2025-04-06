package com.a702.finafan.domain.funding.repository

import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.domain.funding.model.DepositFilter
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.FundingCreateForm
import com.a702.finafan.domain.funding.model.FundingDetail
import com.a702.finafan.domain.funding.model.FundingFilter
import com.a702.finafan.domain.funding.model.MyStar

interface FundingRepository {
    suspend fun getFundingList(filter: FundingFilter): List<Funding>
    suspend fun getFunding(fundingId: Long): FundingDetail
    suspend fun getDepositHistory(fundingId: Long, filter: DepositFilter): List<Deposit>

    suspend fun startFunding(form: FundingCreateForm): Boolean
    suspend fun cancelFunding(cancelDescription: String): Boolean
    suspend fun terminateFunding(): Boolean
    suspend fun updateFundingDesc(fundingDescription: String): Boolean

    suspend fun joinFunding(fundingId: Long): Boolean
    suspend fun leaveFunding(fundingId: Long): Boolean
    suspend fun createDeposit(deposit: Deposit): Boolean
    suspend fun withDrawDeposit(): Boolean // TODO: WithdrawDeposits 모델 파라미터로 추가

    // TODO: 여 밑에 애들 다 모델, Request, Response DTO 추가 必
    suspend fun createPost(): Boolean
    suspend fun getPost()
    suspend fun updatePost()
    suspend fun deletePost(): Boolean

    suspend fun getMyStars(): List<MyStar>
}