package com.a702.finafan.presentation.funding.viewmodel

import com.a702.finafan.common.presentation.BaseState
import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.domain.funding.model.Funding

data class FundingDetailState (
    val funding: Funding? = null,
    val isParticipant: Boolean = false,
    val deposits: List<Deposit> = emptyList(),
    val selectedTab: Int = 0,
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
): BaseState