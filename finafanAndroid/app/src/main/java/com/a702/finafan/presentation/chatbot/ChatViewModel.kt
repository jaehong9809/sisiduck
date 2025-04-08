package com.a702.finafan.presentation.chatbot

import android.util.Log
import androidx.lifecycle.ViewModel
import com.a702.finafan.common.utils.SpeechRecognizerHelper
import com.a702.finafan.domain.chatbot.model.ChatMessage
import com.a702.finafan.domain.chatbot.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
            streamUserMessage(text)
            _uiState.update { it.copy(isListening = false) }
        }

        speechRecognizerHelper.setOnErrorListener {
            _uiState.update { it.copy(isListening = false, toastMessage = "음성 인식 중 오류가 발생했어요.") }
        }

        speechRecognizerHelper.setOnNoResultListener {
            _uiState.update { it.copy(isListening = false, toastMessage = "말씀을 잘 못 알아들었어요.") }
        }
    }

    fun startListening() {
        _uiState.update { it.copy(isListening = true) }
        speechRecognizerHelper.startListening()
    }

    fun cancelListening() {
        _uiState.update { it.copy(isListening = false) }
        speechRecognizerHelper.stopListening()
    }

    fun streamUserMessage(message: String) {
        _uiState.update {
            it.copy(
                messages = it.messages + ChatMessage(message, isUser = true),
                isStreaming = true,
                streamingText = ""
            )
        }

        val builder = StringBuilder()

        repository.streamMessage(
            message = message,
            onChunk = { chunk ->
                Log.d("✅ Streaming Chunk: ", chunk)
                _uiState.update {
                    builder.append(chunk)
                    it.copy(streamingText = it.streamingText + chunk)
                }
            },
            onComplete = {
                Log.d("ChatViewModel", "✅ onComplete 실행")
                val finalText = builder.toString()
                _uiState.update {
                    it.copy(
                        messages = it.messages  + ChatMessage(finalText, isUser = false),
                        streamingText = "",
                        isStreaming = false
                    )
                }
            },
            onError = { throwable ->
                _uiState.update {
                    it.copy(error = throwable, isStreaming = false)
                }
            },
        )
    }

    fun clearToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }

    fun onInputChanged(newText: String) {
        _uiState.update { it.copy(inputText = newText) }
    }

    fun sendTextMessage() {
        val text = uiState.value.inputText.trim()
        if (text.isNotEmpty()) {
            _uiState.update { it.copy(inputText = "") }
            streamUserMessage(text)
        }
    }

    override fun onCleared() {
        super.onCleared()
        speechRecognizerHelper.destroy()
    }
}

