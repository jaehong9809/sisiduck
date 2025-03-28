package com.a702.finafan.presentation.main

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.MainBlack


@Composable
fun CardCarousel(
    modifier: Modifier = Modifier
) {
    val cards = listOf("Card 1", "Card 2", "Card 3")
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = screenWidth * 0.8f
    val peekWidth = (screenWidth - cardWidth) / 2

    val listState = rememberLazyListState()

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
        modifier = Modifier.fillMaxWidth()
    ) {
        items(cards) { card ->
            CardItem(card, cardWidth)
        }
    }
}

@Composable
fun CardItem(title: String, width: Dp) {
    Card(
        modifier = Modifier
            .width(width)
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .shadow(10.dp, spotColor = MainBlack.copy(alpha = 0.05f), shape = RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}


@Preview
@Composable
fun CardCarouselPreview() {
    CardCarousel()
}