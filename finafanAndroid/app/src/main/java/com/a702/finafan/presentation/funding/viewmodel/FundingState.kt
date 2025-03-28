package com.a702.finafan.presentation.funding.viewmodel

import com.a702.finafan.common.presentation.BaseState
import com.a702.finafan.domain.funding.model.Funding

data class FundingState (
    val fundings: List<Funding> = emptyList(),
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
): BaseState
