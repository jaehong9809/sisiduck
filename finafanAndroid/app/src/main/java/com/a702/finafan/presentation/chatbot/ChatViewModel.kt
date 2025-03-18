package com.a702.finafan.presentation.chatbot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.common.utils.SpeechRecognizerHelper
import com.a702.finafan.domain.chatbot.model.ChatMessage
import com.a702.finafan.domain.chatbot.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository,
    private val speechRecognizerHelper: SpeechRecognizerHelper
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    init {
        speechRecognizerHelper.setOnResultListener { text ->
            sendUserMessage(text)
        }
    }

    fun startListening() {
        speechRecognizerHelper.startListening()
    }

    private fun sendUserMessage(text: String) {
        viewModelScope.launch {
            _messages.update { it + ChatMessage(text, true) }
            val reply = repository.sendMessage(text)
            _messages.update { it + ChatMessage(reply, false) }
        }
    }
}

