package com.a702.finafan.data.chatbot.repository

import com.a702.finafan.data.chatbot.datasource.ChatRemoteDataSource
import com.a702.finafan.domain.chatbot.repository.ChatRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val remote: ChatRemoteDataSource
) : ChatRepository {

    override fun streamMessage(question: String): Flow<String> = callbackFlow {
        remote.streamReply(
            message = question,
            onChunk = { trySend(it) },
            onComplete = { close() },
            onError = { close(it) }
        )
        awaitClose {

        }
    }
}


