package com.a702.finafan.presentation.auth

data class AuthState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val openOAuthUrl: String? = null,
    val errorMessage: String? = null
)
