package com.a702.finafan.domain.chatbot.repository

import com.a702.finafan.domain.chatbot.model.ChatMessage

interface ChatRepository {
    suspend fun sendMessage(message: String): ChatMessage
}
