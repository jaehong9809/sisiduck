package com.a702.finafan.domain.chatbot.repository

interface ChatRepository {
    suspend fun sendMessage(message: String): String
}
