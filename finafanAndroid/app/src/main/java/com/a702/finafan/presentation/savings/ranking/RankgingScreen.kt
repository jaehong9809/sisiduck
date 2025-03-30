package com.a702.finafan.presentation.savings.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.ThreeTabRow
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.domain.savings.model.RankingStar
import com.a702.finafan.domain.savings.model.Star

@Composable
fun RankingScreen(
//    viewModel: SavingViewModel = viewModel()
) {

    val list = mutableListOf(RankingStar(Star(entertainerId = 1, entertainerName = "이찬원", entertainerProfileUrl = ""), 1),
        RankingStar(Star(entertainerId = 1, entertainerName = "이찬원", entertainerProfileUrl = ""), 2),
        RankingStar(Star(entertainerId = 1, entertainerName = "이찬원", entertainerProfileUrl = ""), 3))

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CommonBackTopBar(modifier = Modifier, text = "적금 랭킹")

        LazyColumn(
            modifier = Modifier
                .background(MainWhite)
                .weight(1f)
                .fillMaxWidth()
        ) {
            item {
                ThreeTabRow(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    labels = listOf(stringResource(R.string.ranking_daily_title), stringResource(R.string.ranking_weekly_title)),
                    containerColor = MainWhite,
                    selectedTabColor = MainBlack,
                    onTabSelected = listOf(
                        // TODO: 일간, 주간 선택 시 API 호출
//                { viewModel.fetchAllDeposits(fundingId) },
//                { viewModel.fetchMyDeposits(fundingId) }
                    )
                )
            }

            items(list) { rankingStar ->
                RankingItem(rankingStar)
            }

        }
    }

}

@Preview
@Composable
fun RankingPreview() {
    RankingScreen()
}

