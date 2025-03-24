package com.a702.finafan.presentation.savings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.utils.StringUtil

data class SavingData(
    val duration: Int,
    val title: String,
    val amount: Int,
    val account: String,
    val transactionList: MutableList<Transaction>
)

data class Transaction(
    val isFirst: Boolean,
    val title: String,
    val amount: Int,
    val balance: Int,
    val time: String
)

@Composable
fun SavingScreen() {
//    val transactions = mutableListOf(
//        Transaction(true, "이찬원 사랑해", 44444, 64444, "17:05"),
//        Transaction(false, "오늘 너무 귀엽다 찬원아", 10000, 20000, "17:05"),
//        Transaction(false, "20자까지 써볼게요 그러면 어떻게 나올까요", 10000, 20000, "17:05")
//    )

    val transactions = mutableListOf<Transaction>()

    val info = SavingData(30, "찬원이적금", 30400000, "123-456-789000", transactions)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally
    ) {
        item {
            SavingHeader(info)
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        if (info.transactionList.isEmpty()) {
            item {
                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "아직 저축 내역이 없어요.\n" +
                        "저축을 시작해 보세요!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MainTextGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )
            }
        } else {
            items(transactions) { transaction ->
                TransactionItem(transaction)
            }
        }
    }
}

@Composable
fun SavingHeader(info: SavingData) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(560.dp)
            .clip(RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp))
            .background(LightGray)
    ) {
        Text(
            text = "DAY ${info.duration}",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MainWhite,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        )

        Column(
            modifier = Modifier
                .padding(28.dp)
                .align(BottomCenter),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = info.title, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = MainWhite)
            Text(text = StringUtil.formatCurrency(info.amount), fontSize = 36.sp, fontWeight = FontWeight.Bold, color = MainWhite)
            Text(text = info.account, fontSize = 20.sp, fontWeight = FontWeight.Medium, color = MainWhite)
        }
    }
}

@Composable
fun SavingList(transactions: MutableList<Transaction>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        items(transactions) { transaction ->
            TransactionItem(transaction)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSavingScreen() {
    SavingScreen()
}

