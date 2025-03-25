package com.a702.finafan.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.a702.finafan.presentation.chatbot.ChatScreen
import com.a702.finafan.presentation.chatbot.ChatViewModel

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    // ChatScreen for Testing STT
    val chatViewModel: ChatViewModel = hiltViewModel()
    Box(
        modifier = Modifier
    ) {
        ChatScreen(viewModel = chatViewModel)
    }
}