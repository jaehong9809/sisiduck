package com.a702.finafan.presentation.chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.BubbleBlue
import com.a702.finafan.common.ui.theme.BubbleWhite
import com.a702.finafan.domain.chatbot.model.ChatMessage

@Composable
fun ChatBubble(chatMessage: ChatMessage) {
    val backgroundColor = if (chatMessage.isUser) BubbleBlue else BubbleWhite
    val alignment = if (chatMessage.isUser) Arrangement.End else Arrangement.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = alignment
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .widthIn(max = 280.dp)
        ) {
            Text(
                text = chatMessage.message,
                fontSize = 18.sp,
                lineHeight = 22.sp
            )
        }
    }
}
