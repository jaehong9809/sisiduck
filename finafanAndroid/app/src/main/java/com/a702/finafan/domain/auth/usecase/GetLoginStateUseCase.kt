package com.a702.finafan.domain.auth.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.auth.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetLoginStateUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke() : Flow<DataResource<Boolean>> = userRepository.getLoginState()
}