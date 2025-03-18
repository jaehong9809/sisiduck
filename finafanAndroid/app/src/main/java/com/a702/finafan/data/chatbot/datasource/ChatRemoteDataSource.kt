package com.a702.finafan.data.chatbot.datasource

import com.a702.finafan.data.chatbot.api.ChatApi
import com.a702.finafan.data.chatbot.dto.ChatRequest

class ChatRemoteDataSource(
    private val chatApi: ChatApi
) {
    suspend fun getReply(message: String): String =
        chatApi.sendMessage(ChatRequest(message)).reply
}
