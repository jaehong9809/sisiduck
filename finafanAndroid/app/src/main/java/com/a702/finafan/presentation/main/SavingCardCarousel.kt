package com.a702.finafan.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.a702.finafan.common.ui.theme.MainBlack

// TODO: 로그인 구현하면서 dataStore 내에서 유저 상태 구현
sealed class UserState {
    object LoggedOut : UserState()
    data class LoggedIn(val savingsList: List<String>) : UserState()
}

@Composable
fun CardCarousel(
    modifier: Modifier = Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = screenWidth * 0.8f
    val peekWidth = (screenWidth - cardWidth) / 2

    val listState = rememberLazyListState()

    // TODO: 로그인 구현 후 상태 값 받아 오도록 수정
    val userState: UserState = UserState.LoggedOut

    val cards: List<@Composable () -> Unit> = when (userState) {
        is UserState.LoggedOut -> listOf({ LoginContent() })
        is UserState.LoggedIn ->
            // TODO: 지금은 userState가 적금 리스트를 들고 다니는데,
            // TODO: 로그인이 되었다면 API 호출해서 적금 리스트 받아오도록 구현하는 게 나을 것 같슴다
            if (userState.savingsList.isEmpty()) {
                listOf({ CreateSavingContent() })
            } else {
                userState.savingsList.map { saving -> { SavingContent(saving) } }
            }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .collect { isScrolling ->
                if (!isScrolling) {
                    val index = listState.firstVisibleItemIndex
                    val offset = listState.firstVisibleItemScrollOffset
                    val targetIndex = if (offset > cardWidth.value / 2) index + 1 else index

                    listState.scrollToItem(targetIndex, scrollOffset = 0)
                }
            }
    }

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(start = peekWidth, end = peekWidth), // 왼쪽과 오른쪽에 동일한 패딩
        horizontalArrangement = Arrangement.spacedBy(16.dp), // 카드 간 간격
        modifier = modifier.fillMaxWidth()

    ) {
        items(cards) { card ->
            Box(modifier = Modifier.fillParentMaxWidth(), contentAlignment = Alignment.Center) {
                CardItem(card, cardWidth)
            }
        }
    }
}

@Composable
fun CardItem(content: @Composable () -> Unit, width: Dp) {
    Card(
        modifier = Modifier
            .shadow(
                20.dp,
                spotColor = MainBlack.copy(alpha = 0.05f),
                shape = RoundedCornerShape(16.dp)
            )
            .width(width)
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}

@Preview
@Composable
fun PreviewLoggedOut() {
    CardCarouselTest(userState = UserState.LoggedOut)
}

@Preview
@Composable
fun PreviewNoSavings() {
    CardCarouselTest(userState = UserState.LoggedIn(emptyList()))
}

@Preview
@Composable
fun PreviewWithSavings() {
    CardCarouselTest(userState = UserState.LoggedIn(listOf("적금 1", "적금 2", "적금 3")))
}

@Composable
fun CardCarouselTest(userState: UserState) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = screenWidth * 0.8f
    val peekWidth = (screenWidth - cardWidth) / 2
    val listState = rememberLazyListState()

    val cards: List<@Composable () -> Unit> = when (userState) {
        is UserState.LoggedOut -> listOf({ LoginContent() })
        is UserState.LoggedIn -> if (userState.savingsList.isEmpty()) {
            listOf({ CreateSavingContent() })
        } else {
            userState.savingsList.map { saving -> { SavingContent(saving) } }
        }
    }

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(start = peekWidth, end = peekWidth),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(cards) { content ->
            CardItem(content, cardWidth)
        }
    }
}