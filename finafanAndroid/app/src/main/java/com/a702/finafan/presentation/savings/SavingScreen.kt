package com.a702.finafan.presentation.savings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.MainWhite

@Composable
fun SavingScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SavingHeader()
        TransactionList()
    }
}

@Composable
fun SavingHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(560.dp)
            .clip(RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp))
            .background(LightGray),
        contentAlignment = BottomCenter
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "찬원이 적금", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = MainWhite)
            Text(text = "30,400,000원", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = MainWhite)
            Text(text = "123-456-789000", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = MainWhite)
        }
    }
}

@Composable
fun TransactionList() {
    val transactions = listOf(
        Transaction("이찬원 사랑해", "44,444원", "64,444원", "17:05"),
        Transaction("오늘 너무 귀엽다 찬원아", "10,000원", "20,000원", "17:05")
    )

    LazyColumn {
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

@Preview(showBackground = true)
@Composable
fun PreviewSavingHeader() {
    SavingHeader()
}

@Preview(showBackground = true)
@Composable
fun PreviewTransactionList() {
    TransactionList()
}

