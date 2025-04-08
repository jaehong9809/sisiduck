package com.a702.finafan.data.funding.repository

import android.util.Log
import com.a702.finafan.common.data.dto.getOrThrow
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.common.utils.safeApiCall
import com.a702.finafan.data.funding.api.FundingApi
import com.a702.finafan.data.funding.dto.response.toDomain
import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.domain.funding.model.DepositFilter
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.FundingCreateForm
import com.a702.finafan.domain.funding.model.FundingDetail
import com.a702.finafan.domain.funding.model.FundingFilter
import com.a702.finafan.domain.funding.model.MyStar
import com.a702.finafan.domain.funding.model.toData
import com.a702.finafan.domain.funding.repository.FundingRepository
import javax.inject.Inject

class FundingRepositoryImpl @Inject constructor(
    private val api: FundingApi
) : FundingRepository {

    override suspend fun getFunding(fundingId: Long): DataResource<FundingDetail> = safeApiCall {
        api.getFundingDetail(fundingId).getOrThrow { it.toDomain(fundingId) }
    }

    override suspend fun getFundingList(filter: FundingFilter): DataResource<List<Funding>> = safeApiCall {
        api.getFundingList(filter.toString()).getOrThrow { it.map { dto -> dto.toDomain() } }
    }

    override suspend fun joinFunding(fundingId: Long): DataResource<Boolean> = safeApiCall {
        api.joinFunding(fundingId).getOrThrow { true }
    }

    override suspend fun leaveFunding(fundingId: Long): DataResource<Boolean> = safeApiCall {
        TODO("Not yet implemented")
    }

    override suspend fun createDeposit(fundingId: Long, deposit: Deposit): DataResource<Boolean> = safeApiCall {
        api.createDeposit(fundingId, deposit.toData()).getOrThrow { true }
    }

    override suspend fun withDrawDeposit(): DataResource<Boolean> = safeApiCall {
        TODO("Not yet implemented")
    }

    override suspend fun createPost(): DataResource<Boolean> = safeApiCall {
        TODO("Not yet implemented")
    }

    override suspend fun getPost(): DataResource<Unit> = safeApiCall {
        TODO("Not yet implemented")
    }

    override suspend fun updatePost(): DataResource<Unit> = safeApiCall {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(): DataResource<Boolean> = safeApiCall {
        TODO("Not yet implemented")
    }

    override suspend fun getDepositHistory(fundingId: Long, filter: DepositFilter): DataResource<List<Deposit>> = safeApiCall {
        api.getFundingDepositHistory(fundingId, filter.toString()).getOrThrow {
            Log.d("레포임플: ", "${it}")
            it.map { dto -> dto.toDomain() } }
    }

    override suspend fun startFunding(form: FundingCreateForm): DataResource<Boolean> = safeApiCall {
        api.startFunding(form.toData()).getOrThrow { true }
    }

    override suspend fun cancelFunding(cancelDescription: String): DataResource<Boolean> = safeApiCall {
        TODO("Not yet implemented")
    }

    override suspend fun terminateFunding(): DataResource<Boolean> = safeApiCall {
        TODO("Not yet implemented")
    }

    override suspend fun updateFundingDesc(fundingDescription: String): DataResource<Boolean> = safeApiCall {
        TODO("Not yet implemented")
    }

    override suspend fun getMyStars(): DataResource<List<MyStar>> = safeApiCall {
        api.getMyStars().getOrThrow { it.map { dto -> dto.toDomain() } }
    }
}
