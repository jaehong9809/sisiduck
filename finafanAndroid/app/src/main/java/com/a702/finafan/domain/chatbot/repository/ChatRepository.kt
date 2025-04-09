package com.a702.finafan.domain.chatbot.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow


interface ChatRepository {
    fun streamMessage(question: String): Flow<String>
}
