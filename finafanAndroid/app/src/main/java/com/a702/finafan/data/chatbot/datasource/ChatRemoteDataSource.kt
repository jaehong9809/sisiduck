package com.a702.finafan.data.chatbot.datasource

import com.a702.finafan.data.chatbot.api.ChatApi
import com.a702.finafan.data.chatbot.dto.ChatRequest
import javax.inject.Inject

class ChatRemoteDataSource @Inject constructor(
    private val chatApi: ChatApi
) {
    suspend fun getReply(message: String): String =
        chatApi.sendMessage(ChatRequest(message)).reply
}
