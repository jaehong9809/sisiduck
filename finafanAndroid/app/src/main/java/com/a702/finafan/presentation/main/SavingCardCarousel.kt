package com.a702.finafan.presentation.main

import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.domain.main.model.MainSaving
import kotlinx.coroutines.delay
import kotlin.math.abs

@Composable
fun CardCarousel(
    isLoggedIn: Boolean,
    savings: List<MainSaving>,
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val cards: List<@Composable () -> Unit> = when {
        !isLoggedIn -> listOf({ LoginContent(navController) })
        savings.isEmpty() -> listOf({ CreateSavingContent() })
        else -> savings.map { saving -> { SavingContent(saving) } }
    }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = if (cards.size == 1) screenWidth - 32.dp else screenWidth * 0.8f
    val peekWidth = (screenWidth - cardWidth) / 2

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        val interactionSource = listState.interactionSource

        interactionSource.interactions.collect { interaction ->
            if (interaction is DragInteraction.Stop || interaction is DragInteraction.Cancel) {
                delay(100)

                val layoutInfo = listState.layoutInfo
                val visibleItems = layoutInfo.visibleItemsInfo

                if (visibleItems.isNotEmpty()) {
                    val center = layoutInfo.viewportStartOffset +
                            (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset) / 2

                    val closestItem = visibleItems.minByOrNull { item ->
                        val itemCenter = item.offset + item.size / 2
                        abs(itemCenter - center)
                    }

                    closestItem?.let { item ->
                        listState.animateScrollToItem(item.index)
                    }
                }
            }
        }
    }

    val isAtStart by remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }

    val isAtEnd by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == cards.lastIndex ||
                    (listState.firstVisibleItemIndex == cards.lastIndex - 1 &&
                            listState.layoutInfo.visibleItemsInfo.size < cards.size)
        }
    }

    val contentPadding = when {
        cards.size == 1 -> PaddingValues(horizontal = 16.dp)
        isAtStart -> PaddingValues(start = 16.dp, end = 0.dp)
        isAtEnd -> PaddingValues(start = peekWidth, end = 0.dp)
        else -> PaddingValues(start = peekWidth, end = 0.dp)
    }

    LazyRow(
        state = listState,
        contentPadding = contentPadding,
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(cards) { index, card ->
            Box(
                modifier = Modifier
                    .width(cardWidth)
                    .padding(end = if (cards.size == 1) 0.dp else 16.dp),
                contentAlignment = Alignment.Center
            ) {
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