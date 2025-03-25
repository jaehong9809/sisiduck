package com.a702.finafan.data.chatbot.datasource

import com.a702.finafan.BuildConfig
import com.a702.finafan.di.NetworkModule
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class ChatRemoteDataSource @Inject constructor(
    @NetworkModule.AiOkHttpClient private val okHttpClient: OkHttpClient
) {
    fun streamReply(
        message: String,
        onChunk: (String) -> Unit,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val requestBody = """
            {
              "question": "$message"
            }
        """.trimIndent().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("${BuildConfig.AI_URL}/chatbot")
            .post(requestBody)
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // TODO: handle error (ViewModel에서)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.source()?.let { source ->
                    try {
                        while (!source.exhausted()) {
                            val line = source.readUtf8Line() ?: continue
                            if (line.startsWith("data: ")) {
                                val cleanLine = line.removePrefix("data: ").trim() + " "
                                if (cleanLine.isNotEmpty()) {
                                    onChunk(cleanLine) // prefix 제거하고 chunk 추가
                                }
                            }
                        }
                        onComplete()
                    } catch (e: IOException) {
                        // TODO: handle disconnect
                    }
                }
            }
        })
    }
}
