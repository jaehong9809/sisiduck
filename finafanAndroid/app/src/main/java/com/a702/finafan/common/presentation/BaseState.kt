package com.a702.finafan.common.presentation

interface BaseState {
    val isLoading: Boolean
    val error: Throwable?
    val toastMessage: String?
}