package com.a702.finafan.domain.funding.repository

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.domain.funding.model.DepositFilter
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.FundingCreateForm
import com.a702.finafan.domain.funding.model.FundingDetail
import com.a702.finafan.domain.funding.model.FundingFilter
import com.a702.finafan.domain.funding.model.MyStar

interface FundingRepository {
    suspend fun getFundingList(filter: FundingFilter): DataResource<List<Funding>>
    suspend fun getFunding(fundingId: Long): DataResource<FundingDetail>
    suspend fun getDepositHistory(fundingId: Long, filter: DepositFilter): DataResource<List<Deposit>>

    suspend fun startFunding(form: FundingCreateForm): DataResource<Boolean>
    suspend fun cancelFunding(cancelDescription: String): DataResource<Boolean>
    suspend fun terminateFunding(): DataResource<Boolean>
    suspend fun updateFundingDesc(fundingDescription: String): DataResource<Boolean>

    suspend fun joinFunding(fundingId: Long): DataResource<Boolean>
    suspend fun leaveFunding(fundingId: Long): DataResource<Boolean>
    suspend fun createDeposit(fundingId: Long, deposit: Deposit): DataResource<Boolean>
    suspend fun withDrawDeposit(): DataResource<Boolean> // TODO: WithdrawDeposits 모델 파라미터로 추가

    // TODO: 아래 메서드들 모델, Request, Response DTO 설계 필요
    suspend fun createPost(): DataResource<Boolean>
    suspend fun getPost(): DataResource<Unit>
    suspend fun updatePost(): DataResource<Unit>
    suspend fun deletePost(): DataResource<Boolean>

    suspend fun getMyStars(): DataResource<List<MyStar>>
}
