package com.a702.finafan.presentation.savings.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextBlue
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.savings.model.Transaction

@Composable
fun RankingHistoryScreen() {
    val transactions = mutableListOf(
        Transaction(
            amount = 40000,
            balance = 10000,
            message = "이찬원 사랑해",
            date = "2025-03-14",
            imageUrl = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/test_star.jpg"
        ),
        Transaction(
            amount = 40000,
            balance = 10000,
            message = "오늘 너무 귀여워",
            date = "2025-03-14",
            imageUrl = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/test_star.jpg"
        ),
        Transaction(
            amount = 40000,
            balance = 10000,
            message = "진짜 귀엽다",
            date = "2025-03-14",
            imageUrl = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/test_star.jpg"
        ),
    )

    Column {
        CommonBackTopBar(
            modifier = Modifier,
            text = stringResource(R.string.saving_ranking_history_title)
        )

        Row {
            Column {
                // 스타 이름
                Text(
                    text = stringResource(R.string.ranking_star_name, 1, "이찬원"),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    lineHeight = 30.sp,
                    color = MainBlack
                )

                // 총 금액
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = StringUtil.formatCurrency(2300450),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    lineHeight = 24.sp,
                    color = MainBlack
                )
            }

//            GradSubButton(
//                modifier = Modifier.padding(top = 24.dp),
//                text = stringResource(R.string.ranking_my_history),
//                fontSize = 16.sp,
//                onButtonClick = {
//                    // TODO: 나의 응원 내역으로 이동
//                })
        }

    }

//    LazyColumn {
//        items(transactions) { item ->
//            HistoryItem(item)
//        }
//    }
}

@Composable
fun HistoryItem(transaction: Transaction) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))
            .height(74.dp)
            .background(MainWhite),
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
    RankingHistoryScreen()

//    HistoryItem(Transaction(
//        amount = 40000,
//        balance = 10000,
//        message = "이찬원 사랑해",
//        date = "2025-03-14",
//        imageUrl = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/test_star.jpg"
//    ))
}