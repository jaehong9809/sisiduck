package com.a702.finafan.presentation.chatbot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.common.utils.SpeechRecognizerHelper
import com.a702.finafan.domain.chatbot.model.ChatMessage
import com.a702.finafan.domain.chatbot.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository,
    private val speechRecognizerHelper: SpeechRecognizerHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatState())
    val uiState: StateFlow<ChatState> = _uiState.asStateFlow()

    init {
        speechRecognizerHelper.setOnResultListener { text ->
            addUserMessage(text)
            //sendUserMessage(text)
            _uiState.update { it.copy(isListening = false) }
        }
    }

    fun startListening() {
        _uiState.update { it.copy(isListening = true) }
        speechRecognizerHelper.startListening()
    }

    private fun addUserMessage(text: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
        }
    }

    private fun sendUserMessage(text: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching {
                _uiState.update { state -> state.copy(messages = state.messages + ChatMessage(text, true)) }
                val reply = repository.sendMessage(text)
                _uiState.update { state -> state.copy(messages = state.messages + ChatMessage(reply, false)) }
            }.onFailure { throwable ->
                _uiState.update { it.copy(error = throwable) }
            }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}


