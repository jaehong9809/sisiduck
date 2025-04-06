package com.a702.finafan.data.funding.repository

import android.util.Log
import com.a702.finafan.data.funding.api.FundingApi
import com.a702.finafan.data.funding.dto.request.toData
import com.a702.finafan.data.funding.dto.response.toDomain
import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.domain.funding.model.DepositFilter
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.FundingCreateForm
import com.a702.finafan.domain.funding.model.FundingDetail
import com.a702.finafan.domain.funding.model.FundingFilter
import com.a702.finafan.domain.funding.model.MyStar
import com.a702.finafan.domain.funding.repository.FundingRepository
import javax.inject.Inject

class FundingRepositoryImpl @Inject constructor(
    private val api: FundingApi
): FundingRepository {

    override suspend fun getFunding(fundingId: Long): FundingDetail {
        val response = api.getFundingDetail(fundingId)
        
        Log.d("getFundingDetail: ", "${response.data}")

        return if (response.code == "S0000" && response.data != null) {
            response.data.toDomain(fundingId)
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun getFundingList(filter: FundingFilter): List<Funding> {
        val response = api.getFundingList(filter.toString())

        Log.d("getFundingList: ", "${response.data}")

        return if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun joinFunding(fundingId: Long): Boolean {
        val response = api.joinFunding(fundingId)

        Log.d("joinFunding: (RepoImpl)", "${response}")

        if(response.code == "S0000") {
            return true
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun leaveFunding(fundingId: Long): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun createDeposit(deposit: Deposit): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun withDrawDeposit(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun createPost(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getPost() {
        TODO("Not yet implemented")
    }

    override suspend fun updatePost() {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getDepositHistory(
        fundingId: Long,
        filter: DepositFilter
    ): List<Deposit> {
        val response = api.getFundingDepositHistory(fundingId, filter.toString())

        return if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun startFunding(form: FundingCreateForm): Boolean {
        val response = api.startFunding(form.toData())

        if(response.code == "S0000") {
            return true
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun cancelFunding(cancelDescription: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun terminateFunding(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateFundingDesc(fundingDescription: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getMyStars(): List<MyStar> {
        val response = api.getMyStars()

        return if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }
}
