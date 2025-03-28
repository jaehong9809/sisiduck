package com.a702.finafan.data.funding.repository

import android.util.Log
import com.a702.finafan.common.data.dto.ApiResponse
import com.a702.finafan.data.funding.api.FundingApi
import com.a702.finafan.data.funding.dto.AdminUser
import com.a702.finafan.data.funding.dto.Entertainer
import com.a702.finafan.data.funding.dto.EntertainerResponse
import com.a702.finafan.data.funding.dto.FundingApplication
import com.a702.finafan.data.funding.dto.FundingDetailResponse
import com.a702.finafan.data.funding.dto.FundingResponse
import com.a702.finafan.data.funding.dto.toDomain
import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.FundingDetail
import com.a702.finafan.domain.funding.repository.FundingRepository
import java.time.LocalDateTime
import javax.inject.Inject

class FundingRepositoryImpl @Inject constructor(
    private val api: FundingApi
): FundingRepository {

    override suspend fun getFunding(fundingId: Long): FundingDetail {
//        val response = api.getFundingDetail(fundingId)
        val response = ApiResponse(
            code = "S0000",
            message = "정상적으로 처리되었습니다.",
            data = FundingDetailResponse(
                participated = false,
                entertainer = Entertainer(
                    entertainerId = 1, // 백엔드에서 ID 추가하는 게 맞는지 확인 필요
                    name = "이찬원",
                    imageUrl = "https://example.com/profile/chanwon.jpg"
                ),
                adminUser = AdminUser(
                    adminName = "1",
                    description = "이찬원을 사랑해서 커피를 나눠먹으려 합니다. 사랑해!",
                    fundingCount = 7,
                    fundingSuccessCount = 0
                ),
                fundingName = "커피차 서포트",
                goalAmount = 2_000_000L,
                currentAmount = 120_000L,
                fundingExpiryDate = LocalDateTime.parse("2025-04-24T04:50:48.32062"),
                fundingApplication = listOf(
                    FundingApplication(
                        name = "홍길동",
                        balance = 20_000L,
                        content = "내꺼야헤헤헤헤ㅋ.ㅋ",
                        createdAt = LocalDateTime.parse("2025-03-27T13:31:06.236542")
                    ),
                    FundingApplication(
                        name = "홍길동",
                        balance = 20_000L,
                        content = "내꺼야헤헤헤헤ㅋ.ㅋz.z",
                        createdAt = LocalDateTime.parse("2025-03-27T13:31:11.124631")
                    ),
                    FundingApplication(
                        name = "홍길동",
                        balance = 20_000L,
                        content = "내꺼야헤헤헤헤ㅋ.ㅋz.z!!",
                        createdAt = LocalDateTime.parse("2025-03-27T13:31:14.251131")
                    )
                )
            )
        )
        
        Log.d("getFundingDetail: ", "${response.data}")

        return if (response.code == "S0000" && response.data != null) {
            response.data.toDomain(fundingId)
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun getAllFundingList(): List<Funding> {
//        val response = api.getAllFundingList()
        val response = ApiResponse(
            code = "S0000",
            message = "정상적으로 처리되었습니다.",
            data = listOf(
                FundingResponse(
                    entertainer = EntertainerResponse(
                        entertainerId = 1,
                        name = "이찬원",
                        imageUrl = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/leechanwon.png"
                    ),
                    fundingId = 1,
                    fundingName = "이찬원사랑해",
                    currentAmount = 100000,
                    goalAmount = 2000000,
                    fundingExpiryDate = LocalDateTime.parse("2025-04-24T04:50:48.32062")
                )
            )
        )
        Log.d("getAllFundingList: ", "${response.data}")

        return if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun getParticipatingFundingList(): List<Funding> {
        val response = api.getParticipatingFundingList()

        Log.d("getParticipatingFundingList: ", "${response.data}")

        return if (response.code == "S0000" && response.data != null) {
            response.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }

    override suspend fun getMyFundingList(): List<Funding> {
        val response = api.getMyFundingList()

        Log.d("getMyFundingList: ", "${response.data}")

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
