package com.a702.finafan.data.user.model

import com.a702.finafan.common.data.DataMapper
import com.a702.finafan.domain.user.model.User

data class UserInfoResponse(
    val userId: Int,
    val userName: String,
    val userEmail: String
) : DataMapper<User> {
    override fun toDomain() : User {
        return User(
            userId = userId,
            userName = userName.substringBefore('#'),
            userEmail = userEmail,
            profileUrl = ""
        )
    }
}

// TO DO: 나중에 field name 맞추기 or @SerializedName 사용
