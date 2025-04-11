package com.a702.finafan.presentation.funding.viewmodel

import androidx.compose.ui.graphics.Color
import com.a702.finafan.common.presentation.BaseState
import com.a702.finafan.common.ui.theme.starThemes
import com.a702.finafan.data.funding.dto.response.AdminUser
import com.a702.finafan.domain.funding.model.Deposit
import com.a702.finafan.domain.funding.model.Funding

data class FundingDetailState (
    val funding: Funding? = null,
    val isParticipant: Boolean = false,
    val isHost: Boolean = false,
    val hostInfo: AdminUser? = null,
    val description: String = "",
    val deposits: List<Deposit> = emptyList(),
    val selectedTab: Int = 0,
    val colorSet: List<Color> = listOf(
        starThemes[0].start,
        starThemes[0].mid,
        starThemes[0].end
    ),
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
): BaseState