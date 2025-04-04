package com.a702.finafan.presentation.funding.viewmodel

import com.a702.finafan.common.presentation.BaseState
import com.a702.finafan.domain.funding.model.MyStar
import java.time.LocalDate

data class FundingCreateState(
    val selectedStar: MyStar? = null,
    val fundingTitle: String = "",
    val fundingGoal: Long? = null,
    val fundingDescription: String = "",
    val fundingExpiryDate: LocalDate? = null,
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
) : BaseState