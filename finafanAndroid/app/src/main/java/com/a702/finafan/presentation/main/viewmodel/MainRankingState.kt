package com.a702.finafan.presentation.main.viewmodel

import com.a702.finafan.common.presentation.BaseState
import com.a702.finafan.domain.main.model.MainRanking

data class MainRankingState(
    val rankings: List<MainRanking> = emptyList(),
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null
): BaseState