package com.a702.finafan.presentation.chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a702.finafan.common.ui.theme.BubbleBlue
import com.a702.finafan.common.ui.theme.BubbleWhite
import com.a702.finafan.domain.chatbot.model.ChatMessage

@Composable
fun ChatBubble(chatMessage: ChatMessage) {
    val alignment = if (chatMessage.isUser) Alignment.End else Alignment.Start
    val backgroundColor = if (chatMessage.isUser) BubbleBlue else BubbleWhite

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (chatMessage.isUser) Arrangement.End else Arrangement.Start) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(backgroundColor, shape = RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Text(text = chatMessage.message)
        }
    }
}