package com.a702.finafan.domain.auth.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.auth.model.User
import com.a702.finafan.domain.auth.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val userRepository : UserRepository
) {
    operator fun invoke(token : String) : Flow<DataResource<User>> = userRepository.login(token)
}