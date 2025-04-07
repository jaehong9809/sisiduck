package com.a702.finafan.domain.user.model

data class User(
    val userId: Int,
    val userName: String,
    val userEmail: String,
    val profileUrl: String?
)
