package com.a702.finafan.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.a702.finafan.common.ui.component.ThreeTabRow
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainGradBlue
import com.a702.finafan.common.ui.theme.MainGradViolet
import com.a702.finafan.common.ui.theme.MainTextBlue
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.main.model.MainRanking
import com.a702.finafan.domain.main.model.RankingType
import com.a702.finafan.presentation.main.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun MainRanking(
    viewModel: MainViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(RankingType.DAILY) }

    // ViewModel의 상태를 가져옴
    val rankingState by viewModel.mainRankingState.collectAsState()

    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        LaunchedEffect(selectedTab) {
            viewModel.fetchMainRanking(selectedTab)
        }

        ThreeTabRow(
            labels = listOf("일간", "주간", "누적"),
            containerColor = MainWhite,
            selectedTabColor = MainTextBlue,
            onTabSelected = listOf(
                { selectedTab = RankingType.DAILY },
                { selectedTab = RankingType.WEEKLY },
                { selectedTab = RankingType.TOTAL }
            ),
            modifier = Modifier.padding(horizontal = 30.dp)
        )

        if (rankingState.isLoading) {
            Text("로딩 중...", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        } else {
            AutoScrollingRankList(rankingState.rankings)
        }
    }
}

@Composable
fun AutoScrollingRankList(
    rankings: List<MainRanking>,
    intervalMillis: Long = 2000 // 2초마다 변경
) {
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(rankings) {
        while (rankings.isNotEmpty()) {
            delay(intervalMillis)
            currentIndex = (currentIndex + 1) % rankings.size
        }
    }

    if (rankings.isNotEmpty()) {
        RankingCard(rankings[currentIndex])
    } else {
        Text(
            text = "랭킹 데이터 없음",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RankingCard(ranking: MainRanking) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 20.dp, horizontal = 30.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(MainGradBlue, MainGradViolet)
                ),
                shape = RoundedCornerShape(25.dp)
            )
    ) {
        // TODO: API 확인용 임시 UI (수정 예정)
        Text(
            text = "${ranking.rank}위",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = ranking.starName,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "오늘",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = StringUtil.formatCurrency(ranking.amount),
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
