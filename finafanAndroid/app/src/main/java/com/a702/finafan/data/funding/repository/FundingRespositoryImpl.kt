package com.a702.finafan.data.funding.repository

import android.util.Log
import com.a702.finafan.data.funding.api.FundingApi
import com.a702.finafan.data.funding.dto.toDomain
import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.FundingDetail
import com.a702.finafan.domain.funding.model.FundingFilter
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

    override suspend fun getAllDeposits(fundingId: Long): List<Deposit> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyDeposits(fundingId: Long): List<Deposit> {
        TODO("Not yet implemented")
    }
}
