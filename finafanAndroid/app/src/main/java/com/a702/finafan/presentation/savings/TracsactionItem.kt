package com.a702.finafan.presentation.savings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.*
import com.a702.finafan.common.utils.StringUtil

@Composable
fun TransactionItem(transaction: Transaction) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            // 해당 날짜의 첫 번째 항목인 경우에만 날짜 표시
            if (transaction.isFirst) {
                Text(text = "3월 24일", fontSize = 18.sp, color = MainTextGray, fontWeight = FontWeight.Normal, modifier = Modifier.padding(bottom = 24.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = transaction.title, fontSize = 22.sp, color = MainBlack, fontWeight = FontWeight.Medium)
                Text(text = transaction.time, fontSize = 16.sp, color = MainTextGray, fontWeight = FontWeight.Normal)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = StringUtil.formatCurrency(transaction.amount), fontSize = 20.sp, color = MainTextBlue, fontWeight = FontWeight.Bold)
                Text(text = StringUtil.formatCurrency(transaction.balance), fontSize = 16.sp, color = MainTextGray, fontWeight = FontWeight.Medium)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewTransactionItem() {
    TransactionItem(
        transaction = Transaction(true, "20자까지써볼게요그러면어떻게나올까요", 44444, 64444, "17:07")
    )
}