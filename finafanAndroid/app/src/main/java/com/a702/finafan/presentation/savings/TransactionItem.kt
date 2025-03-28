package com.a702.finafan.presentation.savings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextBlue
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.savings.model.Transaction

// 적금 거래 내역 아이템
@Composable
fun TransactionItem(
    transaction: Transaction,
    onSelect: (Transaction) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            // 해당 날짜의 첫 번째 항목인 경우에만 날짜 표시
//            if (transaction.isFirst) {
//                Text(
//                    text = "3월 24일",
//                    fontSize = 18.sp,
//                    color = MainTextGray,
//                    fontWeight = FontWeight.Normal,
//                    modifier = Modifier.padding(bottom = 24.dp)
//                )
//            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        onSelect(transaction)
                    }
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = transaction.message,
                    fontSize = 22.sp,
                    color = MainBlack,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp
                    )

                Text(
                    text = transaction.date,
                    fontSize = 16.sp,
                    color = MainTextGray,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 24.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = StringUtil.formatCurrency(transaction.amount),
                    fontSize = 20.sp,
                    color = MainTextBlue,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 24.sp
                )

                Text(
                    text = StringUtil.formatCurrency(transaction.balance),
                    fontSize = 16.sp,
                    color = MainTextGray,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewTransactionItem() {
        TransactionItem(
            transaction = Transaction(
            amount = 40000,
            balance = 10000,
            message = "오늘 너무 귀여워",
            date = "2025-03-14",
            imageUrl = "https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/test_star.jpg",
        ), onSelect = {}
    )
}