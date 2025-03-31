package com.a702.finafan.presentation.savings.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.ThreeTabRow
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.domain.savings.model.Ranking
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.presentation.navigation.LocalNavController
import com.a702.finafan.presentation.navigation.NavRoutes
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

@Composable
fun RankingScreen(
    viewModel: SavingViewModel = viewModel()
) {

    val navController = LocalNavController.current

    val dailyTitle = stringResource(R.string.ranking_daily_star)
    val weeklyTitle = stringResource(R.string.ranking_weekly_star)

    val tabTitle = remember { mutableStateOf(dailyTitle) }

    val list = mutableListOf(
        Ranking(Star(entertainerId = 1, entertainerName = "이찬원", entertainerProfileUrl = ""), 1, 2345),
        Ranking(Star(entertainerId = 1, entertainerName = "이찬원", entertainerProfileUrl = ""), 2, 2345),
        Ranking(Star(entertainerId = 1, entertainerName = "이찬원", entertainerProfileUrl = ""), 3, 3456),
        Ranking(Star(entertainerId = 1, entertainerName = "이찬원", entertainerProfileUrl = ""), 4, 3456),
        Ranking(Star(entertainerId = 1, entertainerName = "이찬원", entertainerProfileUrl = ""), 5, 3456),
        Ranking(Star(entertainerId = 1, entertainerName = "이찬원", entertainerProfileUrl = ""), 6, 3456),
        Ranking(Star(entertainerId = 1, entertainerName = "이찬원", entertainerProfileUrl = ""), 7, 3456),
        Ranking(Star(entertainerId = 1, entertainerName = "이찬원", entertainerProfileUrl = ""), 8, 3456),
        Ranking(Star(entertainerId = 1, entertainerName = "이찬원", entertainerProfileUrl = ""), 9, 3456),
        Ranking(Star(entertainerId = 1, entertainerName = "이찬원", entertainerProfileUrl = ""), 10, 3456)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CommonBackTopBar(
            modifier = Modifier,
            text = stringResource(R.string.saving_ranking_title),
            isTextCentered = true
        )

        LazyColumn(
            modifier = Modifier
                .background(MainWhite)
                .weight(1f)
                .fillMaxWidth()
        ) {
            item {
                ThreeTabRow(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    labels = listOf(stringResource(R.string.ranking_daily_title), stringResource(R.string.ranking_weekly_title)),
                    containerColor = MainWhite,
                    selectedTabColor = MainBlack,
                    onTabSelected = listOf(
                        // TODO: 일간, 주간 선택 시 API 호출
                        {
                            tabTitle.value = dailyTitle
                        },
                        {
                            tabTitle.value = weeklyTitle
                        }
                    ),
                )
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = stringResource(R.string.ranking_together),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        lineHeight = 24.sp,
                        color = MainTextGray
                    )

                    // TODO: 탭 선택에 따라 일간/주간 타이틀 변경
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = tabTitle.value,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 46.sp,
                        color = MainBlack
                    )
                }
            }

            items(list) { ranking ->
                RankingItem(ranking, onSelect = {
                    viewModel.setRanking(ranking)
                    navController.navigate(NavRoutes.RankingHistory.route)
                })
            }

        }
    }

}

@Preview
@Composable
fun RankingPreview() {
    RankingScreen()
}

