package com.a702.finafan.domain.funding.usecase

import com.a702.finafan.domain.funding.repository.FundingRepository
import javax.inject.Inject

class WithdrawDepositUseCase @Inject constructor(
    private val repository: FundingRepository
) {
}