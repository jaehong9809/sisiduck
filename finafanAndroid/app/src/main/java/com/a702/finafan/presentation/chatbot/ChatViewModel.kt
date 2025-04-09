package com.a702.finafan.presentation.chatbot

import android.app.Application
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.a702.finafan.common.utils.SpeechRecognizerHelper
import com.a702.finafan.common.utils.SpeechRecognizerManager
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
    private val speechRecognizerHelper: SpeechRecognizerHelper,
    app: Application
) : ViewModel() {

    private val manager = SpeechRecognizerManager(app)

    private val _uiState = MutableStateFlow(ChatState())
    val uiState: StateFlow<ChatState> = _uiState.asStateFlow()

    fun attachLifecycle(owner: LifecycleOwner) =
        owner.lifecycle.addObserver(manager)

    fun detachLifecycle(owner: LifecycleOwner) =
        owner.lifecycle.removeObserver(manager)

    fun startListening() {
        manager.start(
            onResult = { text ->
                streamUserMessage(text)
                _uiState.update { it.copy(isListening = false) }
            },
            onErrorToast = { msg ->
                _uiState.update { it.copy(isListening = false, toastMessage = msg) }
            }
        )
        _uiState.update { it.copy(isListening = true) }
    }

    fun cancelListening() {
        manager.cancel()
        _uiState.update { it.copy(isListening = false) }
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

