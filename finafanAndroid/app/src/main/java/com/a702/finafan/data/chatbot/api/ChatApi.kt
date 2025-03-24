package com.a702.finafan.data.chatbot.api

import com.a702.finafan.data.chatbot.dto.ChatRequest
import com.a702.finafan.data.chatbot.dto.ChatResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApi {
    @POST("chatbot")
    suspend fun sendMessage(@Body request: ChatRequest): ChatResponse
}