package com.a702.finafan.data.chatbot.dto

import com.a702.finafan.domain.chatbot.model.ChatMessage

data class ChatResponse(
    val reply: String
)

fun ChatResponse.toDomain(): ChatMessage {
    return ChatMessage(
        message = this.reply,
        isUser = false
    )
}