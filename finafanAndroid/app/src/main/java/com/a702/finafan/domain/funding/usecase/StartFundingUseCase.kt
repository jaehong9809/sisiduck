package com.a702.finafan.domain.funding.usecase

import com.a702.finafan.domain.funding.model.FundingCreateForm
import com.a702.finafan.domain.funding.repository.FundingRepository
import javax.inject.Inject

class StartFundingUseCase @Inject constructor(
    private val repository: FundingRepository
) {
    suspend operator fun invoke(form: FundingCreateForm) {
        repository.startFunding(form)
    }
}