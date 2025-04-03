package com.a702.finafan.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.domain.main.model.MainSaving

// TODO: 로그인 구현하면서 dataStore 내에서 유저 상태 구현
sealed class UserState {
    object LoggedOut : UserState()
    object LoggedIn : UserState()
}

@Composable
fun CardCarousel(
    savings: List<MainSaving>,
    modifier: Modifier = Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = screenWidth * 0.85f
    val peekWidth = (screenWidth - cardWidth) / 2

    val listState = rememberLazyListState()

    // TODO: 로그인 구현 후 상태 값 받아 오도록 수정
    val userState: UserState = UserState.LoggedIn

    val cards: List<@Composable () -> Unit> = when (userState) {
        is UserState.LoggedOut -> listOf({ LoginContent() })
        is UserState.LoggedIn -> if (savings.isEmpty()) {
            listOf({ CreateSavingContent() })
        } else {
            savings.map { saving -> { SavingContent(saving) } }
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
        contentPadding = PaddingValues(start = peekWidth, end = peekWidth),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxWidth()
            .padding(vertical = 8.dp)

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