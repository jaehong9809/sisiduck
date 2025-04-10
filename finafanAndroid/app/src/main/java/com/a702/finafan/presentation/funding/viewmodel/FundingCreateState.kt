package com.a702.finafan.presentation.funding.viewmodel

import com.a702.finafan.common.presentation.BaseState
import com.a702.finafan.domain.funding.model.MyStar
import java.time.LocalDate

data class FundingCreateState(
    val myStars: List<MyStar> = emptyList(),
    val selectedStar: MyStar? = null,
    val fundingTitle: String = "",
    val fundingGoal: Long? = null,
    val fundingDescription: String = "",
    val fundingExpiryDate: LocalDate? = null,
    val isTermAgreed: Boolean = false,
    val isFundingCreated: Boolean = false,
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
) : BaseState {
    val isFormValid: Boolean
        get() = fundingTitle.isNotBlank()
                && fundingGoal != null
                && fundingDescription.length > 100
                && selectedStar != null
                && fundingExpiryDate != null
                && isTermAgreed
}