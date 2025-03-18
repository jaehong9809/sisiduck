package com.a702.finafan.presentation.chatbot

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.common.ui.theme.MainGradBlue
import com.a702.finafan.common.ui.theme.MainGradViolet
import com.a702.finafan.domain.chatbot.model.ChatMessage


@Composable
fun ChatScreen(viewModel: ChatViewModel = viewModel()) {
    val context = LocalContext.current
    val chatMessages by viewModel.messages.collectAsState()

    // 음성 권한 요청
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.startListening()
        } else {
            Toast.makeText(context, "음성 인식 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true
        ) {
            items(chatMessages.reversed()) { chatMessage ->
                ChatBubble(chatMessage)
            }
        }

        GradientButton(
            // runtime 음성 권한 확인
            onClick = {
                when {
                    context.checkSelfPermission(Manifest.permission.RECORD_AUDIO) ==
                            android.content.pm.PackageManager.PERMISSION_GRANTED -> {
                        viewModel.startListening()
                    }
                    else -> {
                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }
                }
            }
        )
    }
}

// 추후 재사용 가능한 Button Component로 교체
@Composable
fun GradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val gradient = Brush.horizontalGradient(
        colors = listOf(MainGradBlue, MainGradViolet)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(84.dp)
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .background(gradient, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "덕순이와 대화하기",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    val sampleMessages = listOf(
        ChatMessage("안녕하세요! 챗봇입니다.", isUser = false),
        ChatMessage("안녕하세요! AI에게 질문할게요.", isUser = true),
        ChatMessage("네, 무엇이든 물어보세요!", isUser = false),
        ChatMessage("안드로이드 STT 기능은 어떻게 작동하나요?", isUser = true),
        ChatMessage("안드로이드 STT는 Google 음성 인식을 기반으로 텍스트 변환을 수행합니다.", isUser = false)
    )

    ChatScreenPreviewContent(sampleMessages)
}

@Composable
fun ChatScreenPreviewContent(messages: List<ChatMessage>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true
        ) {
            items(messages.reversed()) { chatMessage ->
                ChatBubble(chatMessage)
            }
        }

        GradientButton(
            onClick = { /* 미리보기에서 chatbot api 동작하지 않게 */ },
        )
    }
}


