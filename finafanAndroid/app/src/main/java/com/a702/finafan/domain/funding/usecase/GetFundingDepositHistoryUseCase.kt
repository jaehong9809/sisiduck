package com.a702.finafan.domain.funding.usecase

import android.util.Log
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.domain.funding.model.DepositFilter
import com.a702.finafan.domain.funding.repository.FundingRepository
import javax.inject.Inject

class GetFundingDepositHistoryUseCase @Inject constructor(
    private val repository: FundingRepository
) {
    suspend operator fun invoke(fundingId: Long, filter: DepositFilter): DataResource<List<Deposit>> {
        val history: DataResource<List<Deposit>> = repository.getDepositHistory(fundingId, filter)
        Log.d("유스케이스: ", "${history}")
        return history
    }
}