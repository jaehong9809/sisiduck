package com.a702.finafan.presentation.chatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.BubbleBlue
import com.a702.finafan.common.ui.theme.BubbleWhite
import com.a702.finafan.common.ui.theme.MainBgGray
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.TermTextGray
import com.a702.finafan.domain.chatbot.model.ChatMessage

@Composable
fun ChatBubble(chatMessage: ChatMessage) {
    val backgroundColor = if (chatMessage.isUser) BubbleBlue else BubbleWhite
    val alignment = if (chatMessage.isUser) Arrangement.End else Arrangement.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = alignment,
        verticalAlignment = Alignment.Top
    ) {

        if (!chatMessage.isUser) {
            Image(
                painter = painterResource(id = R.drawable.duck),
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 8.dp)
            )
        }

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


@Composable
fun LoadingChatBubble() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {

        Image(
            painter = painterResource(id = R.drawable.duck),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .padding(end = 8.dp)
        )

        Box(
            modifier = Modifier
                .background(BubbleWhite, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .widthIn(max = 280.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.ducksoon_is_thinking),
                    fontSize = 18.sp,
                    color = TermTextGray
                )
                Spacer(modifier = Modifier.width(8.dp))

                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = TermTextGray
                )
            }
        }
    }
}


