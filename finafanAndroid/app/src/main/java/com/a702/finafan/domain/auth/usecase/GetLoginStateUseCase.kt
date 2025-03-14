package com.a702.finafan.domain.auth.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class GetLoginStateUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke() : Flow<DataResource<Boolean>> = authRepository.getLoginState()
}