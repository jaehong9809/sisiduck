package com.a702.finafan.data.user.local

data class UserState(
    val isLoggedIn: Boolean,
    val userId: Long? = null
)