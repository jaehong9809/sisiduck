package com.a702.finafan.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
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
    val pagerState = rememberPagerState(pageCount = { cards.size })

    val contentPadding = if (cards.size == 1) {
        PaddingValues(horizontal = 16.dp)
    } else {
        PaddingValues(horizontal = peekWidth)
    }

    HorizontalPager(
        state = pagerState,
        contentPadding = contentPadding,
        pageSpacing = 16.dp,
        modifier = modifier.fillMaxWidth()
    ) { page ->

        Box(
            modifier = Modifier
                .width(cardWidth),
            contentAlignment = Alignment.Center
        ) {
            CardItem(cards[page], cardWidth)
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
