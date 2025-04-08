package com.a702.finafan.presentation.savings.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.CommonProgress
import com.a702.finafan.common.ui.component.ThreeTabRow
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.domain.main.model.RankingType
import com.a702.finafan.presentation.navigation.LocalNavController
import com.a702.finafan.presentation.navigation.NavRoutes
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

@Composable
fun RankingScreen(
    selectedTabIndex: MutableIntState,
    viewModel: SavingViewModel = viewModel()
) {

    val navController = LocalNavController.current
    val context = LocalContext.current

    val savingState by viewModel.savingState.collectAsState()

    val selectedTab by remember {
        derivedStateOf {
            when (selectedTabIndex.intValue) {
                0 -> RankingType.DAILY
                1 -> RankingType.WEEKLY
                2 -> RankingType.TOTAL
                else -> RankingType.DAILY
            }
        }
    }

    val tabTitle by remember {
        derivedStateOf {
            when (selectedTab) {
                RankingType.DAILY -> context.getString(R.string.ranking_daily_star)
                RankingType.WEEKLY -> context.getString(R.string.ranking_weekly_star)
                RankingType.TOTAL -> context.getString(R.string.ranking_total_star)
            }
        }
    }

    val rankingList by remember {
        derivedStateOf { savingState.rankingList }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchStarRanking(selectedTab)
    }

    Scaffold(
        topBar = {
            CommonBackTopBar(
                modifier = Modifier,
                text = stringResource(R.string.saving_ranking_title),
                isTextCentered = true
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .background(MainWhite)
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                item {
                    ThreeTabRow(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        labels = listOf(
                            stringResource(R.string.ranking_tabrow_daily_label),
                            stringResource(R.string.ranking_tabrow_weekly_label),
                            stringResource(R.string.ranking_tabrow_total_label)
                        ),
                        containerColor = MainWhite,
                        selectedTabColor = MainBlack,
                        selectedIndex = selectedTabIndex,
                        onTabSelected = listOf(
                            {
                                viewModel.fetchStarRanking(RankingType.DAILY)
                            },
                            {
                                viewModel.fetchStarRanking(RankingType.WEEKLY)
                            },
                            {
                                viewModel.fetchStarRanking(RankingType.TOTAL)
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

                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = tabTitle,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            lineHeight = 46.sp,
                            color = MainBlack
                        )
                    }
                }

                when {
                    savingState.isLoading -> {
                        item { CommonProgress() }
                    }
                    else -> {
                        items(rankingList) { ranking ->
                            RankingItem(ranking, onSelect = {
                                navController.navigate(NavRoutes.RankingDetail.route + "/${ranking.starId}/${selectedTab.value}")
                            })
                        }
                    }
                }

            }
        }
    }

}

@Preview
@Composable
fun RankingPreview() {
    RankingScreen(selectedTabIndex = remember { mutableIntStateOf(0) })
}

