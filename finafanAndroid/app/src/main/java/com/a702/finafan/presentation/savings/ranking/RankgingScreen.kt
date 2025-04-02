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
import androidx.compose.runtime.mutableStateOf
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
import com.a702.finafan.common.ui.component.ThreeTabRow
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
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

    val tabTitle = remember {
        mutableStateOf(
            when (selectedTabIndex.value) {
                0 -> context.getString(R.string.ranking_daily_star)
                1 -> context.getString(R.string.ranking_weekly_star)
                2 -> context.getString(R.string.ranking_total_star)
                else -> context.getString(R.string.ranking_daily_star)
            }
        )
    }

    val rankingList by remember {
        derivedStateOf { savingState.rankingList }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchStarRanking(selectedTabIndex.intValue)
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
                                tabTitle.value = context.getString(R.string.ranking_daily_star)
                                viewModel.fetchStarRanking(0)
                            },
                            {
                                tabTitle.value = context.getString(R.string.ranking_weekly_star)
                                viewModel.fetchStarRanking(1)
                            },
                            {
                                tabTitle.value = context.getString(R.string.ranking_total_star)
                                viewModel.fetchStarRanking(2)
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
                            text = tabTitle.value,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            lineHeight = 46.sp,
                            color = MainBlack
                        )
                    }
                }

                items(rankingList) { ranking ->
                    RankingItem(ranking, onSelect = {
                        viewModel.setRanking(ranking)
                        navController.navigate(NavRoutes.RankingHistory.route)
                    })
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

