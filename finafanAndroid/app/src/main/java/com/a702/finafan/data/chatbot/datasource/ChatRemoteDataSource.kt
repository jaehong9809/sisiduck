package com.a702.finafan.data.chatbot.datasource

import com.a702.finafan.data.chatbot.api.ChatApi
import com.a702.finafan.data.chatbot.dto.ChatRequest
import com.a702.finafan.data.chatbot.dto.ChatResponse
import javax.inject.Inject

class ChatRemoteDataSource @Inject constructor(
    private val chatApi: ChatApi
) {
    suspend fun getReply(message: String): ChatResponse =
        chatApi.sendMessage(ChatRequest(message))
}