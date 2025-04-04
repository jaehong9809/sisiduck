package com.a702.finafan.presentation.savings.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.CommonProgress
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextBlue
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.main.model.RankingType
import com.a702.finafan.domain.savings.model.Transaction
import com.a702.finafan.presentation.navigation.LocalNavController
import com.a702.finafan.presentation.navigation.NavRoutes
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

@Composable
fun RankingDetailScreen(
    viewModel: SavingViewModel = viewModel(),
    starId: Long,
    type: String
) {

    val navController = LocalNavController.current
    val savingState by viewModel.savingState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchStarRankingDetail(starId, RankingType.fromValue(type) ?: RankingType.DAILY)
    }

    Scaffold(
        topBar = {
            CommonBackTopBar(
                modifier = Modifier,
                text = stringResource(R.string.saving_ranking_history_title),
                textOnClick = {
                    navController.popBackStack()
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(MainBgLightGray)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 스타 이름
                        Text(
                            text = stringResource(
                                R.string.ranking_star_name,
                                savingState.ranking.rank,
                                savingState.ranking.starName
                            ),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            lineHeight = 30.sp,
                            color = MainBlack
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // 총 금액
                        Text(
                            text = StringUtil.formatCurrency(savingState.ranking.totalAmount),
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            lineHeight = 24.sp,
                            color = MainBlack
                        )
                    }
                }

                when {
                    savingState.isLoading -> {
                        item { CommonProgress() }
                    }
                    else -> {
                        items(savingState.ranking.transactions) { item ->
                            HistoryItem(item, onSelect = {
                                viewModel.setTransaction(item)
                                navController.navigate(NavRoutes.TransactionDetail.route)
                            })
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun HistoryItem(
    transaction: Transaction,
    onSelect: (Transaction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(25.dp))
            .height(74.dp)
            .background(MainWhite)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    onSelect(transaction)
                }
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, end = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = transaction.message,
                fontSize = 18.sp,
                color = MainBlack,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = StringUtil.formatCurrency(transaction.balance),
                fontSize = 18.sp,
                color = MainTextBlue,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Preview
@Composable
fun RankingHistoryPreview() {
    RankingDetailScreen(starId = 0, type = "total")

//    HistoryItem(Transaction(
//        amount = 40000,
//        balance = 10000,
//        message = "이찬원 사랑해",
//        date = "2025-03-14",
//        imageUrl = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/test_star.jpg"
//    ))
}