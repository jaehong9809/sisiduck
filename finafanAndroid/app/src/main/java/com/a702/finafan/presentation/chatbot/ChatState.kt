package com.a702.finafan.presentation.chatbot

import com.a702.finafan.common.presentation.BaseState
import com.a702.finafan.domain.chatbot.model.ChatMessage

data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    override val toastMessage: String? = null
) : BaseState
