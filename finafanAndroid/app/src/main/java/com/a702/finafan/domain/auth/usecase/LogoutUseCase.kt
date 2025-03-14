package com.a702.finafan.domain.auth.usecase

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke() : Flow<DataResource<Unit>> = authRepository.logout()
}