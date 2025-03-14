package com.a702.finafan.data.user.model

import com.a702.finafan.common.data.DataMapper
import com.a702.finafan.domain.user.model.User

data class UserEntity(
    val userId: Int,
    val userName: String,
    val profileUrl: String?
) : DataMapper<User> {
    override fun toDomain() : User {
        return User(
            userId = userId,
            userName = userName,
            profileUrl = profileUrl
        )
    }
}

fun User.toData(): UserEntity{
    return UserEntity(
        userId = userId,
        userName = userName,
        profileUrl = profileUrl
    )
}
