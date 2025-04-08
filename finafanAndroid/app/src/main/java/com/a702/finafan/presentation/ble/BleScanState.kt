package com.a702.finafan.presentation.ble

import com.a702.finafan.common.presentation.BaseState

data class BleScanState(
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null
) : BaseState