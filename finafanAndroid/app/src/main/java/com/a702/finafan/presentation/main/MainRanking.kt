package com.a702.finafan.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.ThreeTabRow
import com.a702.finafan.common.ui.theme.MainGradBlue
import com.a702.finafan.common.ui.theme.MainGradViolet
import com.a702.finafan.common.ui.theme.MainTextBlue
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.Shadow.dropShadow
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.main.model.MainRanking
import com.a702.finafan.domain.main.model.RankingType
import com.a702.finafan.presentation.main.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun MainRanking(
    viewModel: MainViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableStateOf(RankingType.DAILY) }

    val rankingState by viewModel.mainRankingState.collectAsState()

    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        LaunchedEffect(selectedTab) {
            viewModel.fetchMainRanking(selectedTab)
        }

        ThreeTabRow(
            labels = listOf(
                stringResource(R.string.ranking_tabrow_daily_label),
                stringResource(R.string.ranking_tabrow_weekly_label),
                stringResource(R.string.ranking_tabrow_total_label)),
            containerColor = MainWhite,
            selectedTabColor = MainTextBlue,
            onTabSelected = listOf(
                { selectedTab = RankingType.DAILY },
                { selectedTab = RankingType.WEEKLY },
                { selectedTab = RankingType.TOTAL }
            ),
            modifier = Modifier.padding(horizontal = 30.dp)
        )

        AutoScrollingRankList(rankings = rankingState.rankings, rankingType = selectedTab)
    }
}

@Composable
fun AutoScrollingRankList(
    rankings: List<MainRanking>,
    rankingType: RankingType,
    intervalMillis: Long = 3000 // 3초마다 변경
) {
    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(rankings) {
        if (rankings.isNotEmpty() && rankings.size > 1) {
            while (true) {
                delay(intervalMillis)
                currentIndex = (currentIndex + 1) % rankings.size
            }
        } else {
            currentIndex = 0
        }
    }

    if (rankings.isNotEmpty()) {
        RankingCard(rankings[currentIndex], rankingType)
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.opened_box),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier
                    .width(120.dp)
                    .padding(top = 25.dp)
            )
            Text(
                text = stringResource(R.string.ranking_empty_message),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
fun RankingCard(ranking: MainRanking, rankingType: RankingType) {
    val rankingPeriodText = when (rankingType) {
        RankingType.DAILY -> stringResource(R.string.ranking_card_daily_label)
        RankingType.WEEKLY -> stringResource(R.string.ranking_card_weekly_label)
        RankingType.TOTAL -> stringResource(R.string.ranking_card_total_label)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 20.dp, horizontal = 30.dp)
            .dropShadow(
                shape = RoundedCornerShape(25.dp),
                offsetX = 2.dp
            )
            .dropShadow(
                shape = RoundedCornerShape(25.dp),
                offsetX = (-2).dp,
                offsetY = (-2).dp,
                color = MainWhite
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(MainGradBlue, MainGradViolet)
                ),
                shape = RoundedCornerShape(25.dp)
            ),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 34.dp)
        ) {
            Text(
                text = "${ranking.rank}${stringResource(R.string.ranking_card_rank_text)}",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Left,
                color = MainWhite,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = ranking.starName,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                color = MainWhite,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = rankingPeriodText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Left,
                color = MainWhite,
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 3.dp)
            )
            Text(
                text = StringUtil.formatCurrency(ranking.amount),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Left,
                color = MainWhite,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Image(
            painter = rememberAsyncImagePainter(ranking.starImageUrl),
            contentDescription = ranking.starName,
            modifier = Modifier
        )
    }
}