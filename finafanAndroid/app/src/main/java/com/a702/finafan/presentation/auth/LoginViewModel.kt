package com.a702.finafan.presentation.auth

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.data.user.local.UserPreferences
import com.a702.finafan.domain.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val ssafyOauthUrl = "https://project.ssafy.com/oauth/sso-check";
    private val _uiState = MutableStateFlow(AuthState())
    val uiState = _uiState.asStateFlow()

    private fun buildOAuthUrl(
        clientId: String,
        redirectUri: String
    ): String {
        return Uri.parse(ssafyOauthUrl)
            .buildUpon()
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("redirect_uri", redirectUri)
            .appendQueryParameter("response_type", "code")
            .build()
            .toString()
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val clientId = "0d62ba8e-d889-4fd7-91a7-90a27e517499"
                val redirectUri = "https://j12a702.p.ssafy.io/api/v1/auth/login/ssafy"
                val url = buildOAuthUrl(clientId, redirectUri)

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
                    userPreferences.setLoginState(true)
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