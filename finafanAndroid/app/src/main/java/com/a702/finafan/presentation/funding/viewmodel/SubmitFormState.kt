package com.a702.finafan.presentation.funding.viewmodel

import com.a702.finafan.common.presentation.BaseState
import com.a702.finafan.domain.funding.model.UsageItem

data class SubmitFormState (
    val content: String = "",
    val usageList: List<UsageItem> = emptyList(),
    val imageList: List<String> = emptyList(),
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null,
) : BaseState