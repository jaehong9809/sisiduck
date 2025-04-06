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
        Log.d("FundingRepositoryImpl", ">>> getFundingList() 호출됨")
        return try {
            val response = api.getFundingList(filter.toString())
            Log.d("FundingRepositoryImpl", ">>> API 응답 성공: ${response.data}")

            if (response.code == "S0000" && response.data != null) {
                response.data.map { it.toDomain() }
            } else {
                throw Exception(response.message)
            }
        } catch (e: Exception) {
            Log.e("FundingRepositoryImpl", "❌ 예외 발생: ${e.message}", e)

            // Retrofit이 뱉는 HTTP 에러 확인
            if (e is retrofit2.HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("FundingRepositoryImpl", "❌ HTTP 상태코드: ${e.code()}, 에러 바디: $errorBody")
            }

            // Gson 등 JSON 파싱 예외 확인
            if (e is com.google.gson.JsonParseException) {
                Log.e("FundingRepositoryImpl", "❌ JSON 파싱 에러: ${e.message}")
            }

            throw e
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
