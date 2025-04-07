package com.a702.finafan.domain.user.usecase

import com.a702.finafan.domain.user.model.User
import com.a702.finafan.domain.user.repository.UserRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User {
        return userRepository.getUserInfo()
    }
}