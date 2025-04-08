package com.a702.finafan.presentation.chatbot

import com.a702.finafan.common.presentation.BaseState
import com.a702.finafan.domain.chatbot.model.ChatMessage

data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    val isListening: Boolean = false,
    val isStreaming: Boolean = false,
    val streamingText: String = "",
    val inputText: String = "",
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null
) : BaseState
