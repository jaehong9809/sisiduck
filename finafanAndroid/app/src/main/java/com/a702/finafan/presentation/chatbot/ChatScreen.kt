package com.a702.finafan.presentation.chatbot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChatScreen(viewModel: ChatViewModel = viewModel()) {
    val chatMessages by viewModel.chatMessages.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        ChatList(chatMessages)

        Button(onClick = { viewModel.startListening() }) {
            Text("음성 녹음")
        }
    }
}
