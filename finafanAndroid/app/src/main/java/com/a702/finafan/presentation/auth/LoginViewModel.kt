package com.a702.finafan.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.domain.auth.repository.AuthRepository
import com.a702.finafan.domain.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val ssafyOauthUrl = "https://project.ssafy.com/oauth/sso-check";
    private val _uiState = MutableStateFlow(AuthState())
    val uiState = _uiState.asStateFlow()

    fun onLoginClicked() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val url = ssafyOauthUrl
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        openOAuthUrl = url
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun onOAuthCallbackReceived(accessToken: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                authRepository.saveAccessToken(accessToken)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        openOAuthUrl = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

}