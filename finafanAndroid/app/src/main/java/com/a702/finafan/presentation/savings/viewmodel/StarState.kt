package com.a702.finafan.presentation.savings.viewmodel

import com.a702.finafan.common.presentation.BaseState
import com.a702.finafan.domain.savings.model.Star

data class StarState(
    val stars: List<Star> = emptyList(),
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null
): BaseState