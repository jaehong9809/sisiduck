package com.a702.finafan.data.chatbot.repository

import com.a702.finafan.data.chatbot.api.ChatApi
import com.a702.finafan.data.chatbot.dto.ChatRequest
import com.a702.finafan.domain.chatbot.repository.ChatRepository

class ChatRepositoryImpl(private val chatApi: ChatApi) : ChatRepository {
    override suspend fun sendMessage(message: String): String {
        val response = chatApi.sendMessage(ChatRequest(message))
        return response.reply
    }
}
