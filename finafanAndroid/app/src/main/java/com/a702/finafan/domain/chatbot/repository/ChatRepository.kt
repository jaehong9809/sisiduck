package com.a702.finafan.domain.chatbot.repository


interface ChatRepository {
    fun streamMessage(
        message: String,
        onChunk: (String) -> Unit,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    )
}
