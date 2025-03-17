package com.a702.finafan.presentation.chatbot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a702.finafan.domain.chatbot.model.ChatMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository
) : ViewModel() {

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages

    fun addUserMessage(message: String) {
        _chatMessages.value += ChatMessage(message, isUser = true)

        viewModelScope.launch {
            val reply = repository.sendMessage(message)
            _chatMessages.update { it + ChatMessage(reply, false) }
        }
    }
}
