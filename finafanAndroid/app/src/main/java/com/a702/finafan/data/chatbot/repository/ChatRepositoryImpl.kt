package com.a702.finafan.data.chatbot.repository

import com.a702.finafan.data.chatbot.datasource.ChatRemoteDataSource
import com.a702.finafan.domain.chatbot.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val remote: ChatRemoteDataSource
) : ChatRepository {
    override fun streamMessage(
        message: String,
        onChunk: (String) -> Unit,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        remote.streamReply(
            message = message,
            onChunk = onChunk,
            onComplete = onComplete,
            onError = onError
        )
    }
}


