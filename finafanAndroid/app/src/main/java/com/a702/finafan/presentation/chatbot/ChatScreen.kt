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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainGradBlue
import com.a702.finafan.common.ui.theme.MainGradViolet
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.Pretendard
import com.a702.finafan.domain.chatbot.model.ChatMessage
import com.dotlottie.dlplayer.Mode
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import kotlinx.coroutines.launch


@Composable
fun ChatScreen(viewModel: ChatViewModel = viewModel()) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.messages.size, uiState.streamingText) {
        coroutineScope.launch {
            listState.animateScrollToItem(uiState.messages.size)
        }
    }

    val showScrollToBottomButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex < uiState.messages.lastIndex
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.startListening()
        } else {
            Toast.makeText(context, context.getString(R.string.permission_audio_required), Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            Toast.makeText(context, it.message ?: context.getString(R.string.error_audio), Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearToastMessage()
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 12.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(bottom = 84.dp), // 버튼 높이 고려
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.messages) { ChatBubble(it) }

                if (uiState.isStreaming && uiState.streamingText.isNotBlank()) {
                    item {
                        ChatBubble(ChatMessage(uiState.streamingText, isUser = false))
                    }
                }
            }
        }

        if (uiState.isListening) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DotLottieAnimation(
                    source = DotLottieSource.Url("https://lottie.host/cbdfd462-2890-4e31-89a5-cc1ff1d2d688/MzEAG2h9z8.lottie"),
                    autoplay = true,
                    loop = true,
                    speed = 3f,
                    useFrameInterpolation = false,
                    playMode = Mode.FORWARD,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.ducksoon_is_listening),
                    fontSize = 24.sp,
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF37474F)
                )
            }
        }

        GradientButton(
            onClick = {
                when {
                    context.checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                            == android.content.pm.PackageManager.PERMISSION_GRANTED -> {
                        viewModel.startListening()
                    }
                    else -> {
                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }
                }
            },
            buttonText = stringResource(R.string.chatbot_talk_button),
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )

        if (showScrollToBottomButton) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(uiState.messages.size)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 96.dp, end = 16.dp),
                containerColor = MainWhite,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.down_scroll),
                    color = MainBlack,
                    fontSize = 20.sp,
                )
            }
        }
    }
}


// 추후 재사용 가능한 Button Component로 교체
@Composable
fun GradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonText: String
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
            text = buttonText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}



