package com.a702.finafan.presentation.chatbot

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.BackWhite
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainGradViolet
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.domain.chatbot.model.ChatMessage
import com.dotlottie.dlplayer.Mode
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import kotlinx.coroutines.launch


@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel()
) {

    /* ─── State & helpers ─── */
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val owner = LocalLifecycleOwner.current

    /* 새 메시지 도착 시 자동 스크롤 */
    LaunchedEffect(uiState.messages.size, uiState.streamingText) {
        scope.launch { listState.animateScrollToItem(uiState.messages.size) }
    }

    /* “맨 아래로” 버튼 표시 여부 */
    val showScrollToBottom by remember {
        derivedStateOf {
            val notAtBottom = listState.firstVisibleItemIndex < uiState.messages.lastIndex
            val scrollable  = listState.layoutInfo.totalItemsCount > 5
            notAtBottom && scrollable
        }
    }

    /* RECORD_AUDIO 권한 런처 */
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) viewModel.startListening()
        else Toast.makeText(
            context,
            context.getString(R.string.permission_audio_required),
            Toast.LENGTH_SHORT
        ).show()
    }

    /* Speech Manager 통한 LifeCycle 관리 */
    DisposableEffect(owner) {
        viewModel.attachLifecycle(owner)
        onDispose { viewModel.detachLifecycle(owner) }
    }

    /* Error Toast */
    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            Toast.makeText(
                context,
                it.message ?: context.getString(R.string.error_audio),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearToastMessage()
        }
    }

    /* ─── Scaffold ─── */
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { ChatInputBar(viewModel) },
        floatingActionButton = {
            VoiceFab (
                onClick = {
                    if (context.checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        viewModel.startListening()
                    } else {
                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets
    ) { innerPadding ->

        /* ─── 본문(Box) ─── */
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)  // bottomBar + 시스템 inset
        ) {
            /* 메시지 리스트 */
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(
                    top = 12.dp,
                    bottom = 28.dp
                )
            ) {
                items(uiState.messages) { ChatBubble(it) }

                if (uiState.isStreaming) {
                    item(key = "streaming") {
                        if (uiState.streamingText.isEmpty() && uiState.isLoading) LoadingChatBubble()
                        else ChatBubble(ChatMessage(uiState.streamingText, isUser = false))
                    }
                }
            }

            if (uiState.isListening) {
                ListeningDialog(
                    onStopListening = { viewModel.cancelListening() }
                )
            }

            /* 맨 아래 이동 버튼 */
            AnimatedVisibility(
                visible = showScrollToBottom,
                enter = fadeIn() + slideInVertically { it },
                exit  = fadeOut() + slideOutVertically { it },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 96.dp)
            ) {
                FloatingActionButton(
                    onClick = {
                        scope.launch { listState.animateScrollToItem(uiState.messages.size) }
                    },
                    modifier = Modifier.size(48.dp),
                    containerColor = MainWhite,
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = stringResource(R.string.down_scroll),
                        tint = MainBlack
                    )
                }
            }
        }
    }
}

@Composable
fun ListeningDialog(
    onStopListening: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = { /* 뒤로가기 방지 */ }) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(BackWhite, shape = RoundedCornerShape(20.dp))
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // 🐥 덕순이
                Image(
                    painter = painterResource(id = R.drawable.duck),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                DotLottieAnimation(
                    source = DotLottieSource.Url("https://lottie.host/cbdfd462-2890-4e31-89a5-cc1ff1d2d688/MzEAG2h9z8.lottie"),
                    autoplay = true,
                    loop = true,
                    speed = 3f,
                    useFrameInterpolation = false,
                    playMode = Mode.FORWARD,
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.ducksoon_is_listening),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MainBlack
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 🛑 Stop Button
                Button(
                    onClick = onStopListening,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainGradViolet)
                ) {
                    Text(
                        text = stringResource(R.string.voice_cancel),
                        color = MainWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}
