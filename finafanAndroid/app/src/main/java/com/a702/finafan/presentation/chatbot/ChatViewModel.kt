package com.a702.finafan.presentation.chatbot

import android.app.Application
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.common.utils.SpeechRecognizerHelper
import com.a702.finafan.common.utils.SpeechRecognizerManager
import com.a702.finafan.domain.chatbot.model.ChatMessage
import com.a702.finafan.domain.chatbot.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        _uiState.update { it.copy(isListening = false, error = null) }
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

    private fun streamUserMessage(message: String) {
        repository.streamMessage(message)
            /* 1) 스트림 시작 → 유저 메시지 먼저 추가 */
            .onStart {
                _uiState.update {
                    it.copy(
                        messages      = it.messages + ChatMessage(message, isUser = true),
                        streamingText = "",          // 빈 봇 말풍선 내용
                        isStreaming   = true
                    )
                }
            }

            /* 2) chunk 가 들어올 때마다 로그 + UI 갱신 */
            .onEach { chunk ->
                Log.d("✅ StreamingChunk: ", "\"$chunk\"  (on ${Thread.currentThread().name})")
                _uiState.update { state ->
                    state.copy(streamingText = state.streamingText + chunk)
                }
            }

            /* 3) 스트림 종료(정상/에러) */
            .onCompletion { cause ->
                _uiState.update { state ->
                    if (cause == null) {              // 정상 완료
                        state.copy(
                            messages      = state.messages + ChatMessage(state.streamingText, isUser = false),
                            streamingText = "",
                            isStreaming   = false
                        )
                    } else {                          // 예외 발생
                        state.copy(error = cause, isStreaming = false)
                    }
                }
            }

            /* 4) Flow 실행 – viewModelScope 안에서 */
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        manager.cancel()
    }
}

