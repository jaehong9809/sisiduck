package com.a702.finafan.presentation.savings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.common.ui.Shadow.innerShadow
import com.a702.finafan.common.ui.theme.MainBlackWithTransparency
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.utils.StringUtil

@Composable
fun TransactionDetail(transaction: Transaction) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/test_star.jpg")
            .build()
    )

//    CommonTopBarWithEndText(onClick = , text = "내역 보기")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .innerShadow(shape = RectangleShape,
                color = MainBlackWithTransparency, blur = 100.dp, offsetX = 0.dp, offsetY = (-10).dp, spread = 0.dp)
    ) {

        Image(
            painter = painter,
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize()
                .fillMaxSize()
                .background(LightGray)
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )

        // TODO: 이미지가 없으면 디폴트 gradient 띄우기

        Column(
            modifier = Modifier
                .padding(bottom = 64.dp, start = 24.dp, end = 24.dp)
                .align(BottomCenter),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = transaction.time, fontSize = 20.sp, fontWeight = FontWeight.Medium, color = MainWhite)
            Text(text = transaction.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MainWhite)
            Text(text = StringUtil.formatCurrency(transaction.amount), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MainWhite)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTransactionDetail() {
    TransactionDetail(
        transaction = Transaction(true, "20자까지써볼게요그러면어떻게나올까요", 44444, 64444, "2025년 3월 24일 17:07")
    )
}