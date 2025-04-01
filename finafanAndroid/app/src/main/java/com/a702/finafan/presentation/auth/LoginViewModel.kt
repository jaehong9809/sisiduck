package com.a702.finafan.presentation.auth

import androidx.lifecycle.ViewModel
import com.a702.finafan.domain.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {


}