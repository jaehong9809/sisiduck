package com.a702.finafan.presentation.funding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.Pretendard
import com.a702.finafan.common.ui.theme.Typography
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.funding.model.Deposit
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DepositHistoryItem(
    depositorName: String,
    time: LocalDateTime,
    amount: Long
) {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 30.dp, start = 5.dp, end = 5.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = depositorName, fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Pretendard, color = MainBlack)
            Text(text = time.format(DateTimeFormatter.ofPattern("HH:mm")), style = Typography.titleLarge,
                color = MainTextGray, modifier = Modifier.align(alignment = Alignment.Bottom))
        }
        Text(
            text = StringUtil.formatCurrency(amount),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = Pretendard,
            color = MainBlack,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun DepositHistoryList(deposits: List<Deposit>) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)) {
        deposits.forEach { deposit ->
            DepositHistoryItem(deposit.message, deposit.createdAt, deposit.balance)
        }
    }
}

//@Preview
//@Composable
//fun DepositHistoryPreview() {
//    val deposits = listOf(
//        Deposit("2025", "3월 17일", "입금자명", "09:48", 44444),
//        Deposit("2025", "3월 16일", "입금자명", "15:34", 44444),
//        Deposit("2025", "3월 16일", "입금자명", "15:28", 44444)
//    )
//    DepositHistoryList(deposits)
//}
