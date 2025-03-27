package com.a702.finafan.data.funding.repository

import com.a702.finafan.data.funding.api.FundingApi
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.repository.FundingRepository
import javax.inject.Inject

//class FundingRepositoryImpl @Inject constructor(
//    private val fundingApi: FundingApi
//) : FundingRepository {
//
//    override fun getFundingList(): List<Funding> {
//        val fundingResponseList = fundingApi.getFundingList()
//        return fundingResponseList.map { it.toFunding() } // DTO -> 도메인 모델 변환
//    }
//
//    suspend fun getFundingDetail(fundingId: Long): FundingDetail {
//        val fundingDetailResponse = fundingApi.getFundingDetail(fundingId)
//        return fundingDetailResponse.toFundingDetail() // DTO -> 도메인 모델 변환
//    }
//
//    suspend fun joinFunding(fundingId: Long) {
//        fundingApi.joinFunding(fundingId)
//    }
//}
