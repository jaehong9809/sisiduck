package com.a702.finafan.data.chatbot.repository

import com.a702.finafan.data.chatbot.datasource.ChatRemoteDataSource
import com.a702.finafan.domain.chatbot.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val dataSource: ChatRemoteDataSource
) : ChatRepository {
    override suspend fun sendMessage(message: String): String {
        val response = dataSource.getReply(message)
        return response
    }
}

