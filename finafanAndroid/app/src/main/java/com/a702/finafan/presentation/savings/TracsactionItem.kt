package com.a702.finafan.presentation.savings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.MainBlack

data class Transaction(val title: String, val amount: String, val balance: String, val time: String)

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = transaction.title, fontSize = 22.sp, color = MainBlack, fontWeight = FontWeight.Medium)
            Text(text = transaction.time, fontSize = 16.sp, color = Gray, fontWeight = FontWeight.Normal)
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = transaction.amount, fontSize = 20.sp, color = Blue, fontWeight = FontWeight.Bold)
            Text(text = transaction.balance, fontSize = 16.sp, color = Gray, fontWeight = FontWeight.Medium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTransactionItem() {
    TransactionItem(
        transaction = Transaction("이찬원 사랑해", "44,444원", "64,444원", "17:07")
    )
}