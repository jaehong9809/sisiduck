package com.a702.finafan.presentation.savings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.utils.StringUtil

import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.common.ui.theme.MainBlack

import android.graphics.drawable.BitmapDrawable
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.a702.finafan.common.ui.Shadow.innerShadow
import com.a702.finafan.common.ui.theme.MainBlackWithTransparency

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

fun extractDominantColor(bitmap: Bitmap): Int {
    val palette = Palette.from(bitmap).generate()
    return palette.getDominantColor(0x000000) // 기본값은 검정색
}

fun extractDominantColorFromDrawable(drawable: Drawable): Int {
    val bitmap = drawable.toBitmap()
    return extractDominantColor(bitmap)
}

@Composable
fun SavingHeader(info: SavingData) {
    var dominantColor by remember { mutableStateOf<Color>(MainBlack) }

//    val painter = rememberAsyncImagePainter(
//        model = ImageRequest.Builder(LocalContext.current)
//            .data("https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/00131df0-455a-4f3f-974d-18e2ebcf77e8_1739945302224.png")
//            .build(),
//        onSuccess = { result ->
//            val drawable = result.result.drawable
//
//            // drawable이 BitmapDrawable인지 확인 후 Bitmap으로 변환
//            val bitmap = (drawable as? BitmapDrawable)?.bitmap
//                ?: (drawable as? VectorDrawable)?.toBitmap() // VectorDrawable을 Bitmap으로 변환
//
//            if (bitmap != null) {
//                // Bitmap에서 Dominant 색상 추출
//                val dominantColorInt = extractDominantColor(bitmap)
//                dominantColor = Color(dominantColorInt)
//            } else {
//                // Bitmap으로 변환할 수 없는 경우 기본 색상 설정
//                dominantColor = MainBlack
//            }
//        }
//    )

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://a407-20250124.s3.ap-northeast-2.amazonaws.com/images/00131df0-455a-4f3f-974d-18e2ebcf77e8_1739945302224.png")
            .build()
    )

    val textColor = if (ColorUtils.calculateLuminance(dominantColor.toArgb()) > 0.5) {
        MainBlack
    } else {
        MainWhite
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(560.dp)
            .clip(RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp))
            .innerShadow(shape = RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp),
                color = MainBlackWithTransparency, blur = 50.dp, offsetX = 0.dp, offsetY = (-10).dp, spread = 0.dp)
    ) {

        Image(
            painter = painter,
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize()
                .height(560.dp)
                .clip(RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp))
                .background(LightGray)
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "DAY ${info.duration}",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
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
            Text(text = info.title, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = textColor)
            Text(text = StringUtil.formatCurrency(info.amount), fontSize = 36.sp, fontWeight = FontWeight.Bold, color = textColor)
            Text(text = info.account, fontSize = 20.sp, fontWeight = FontWeight.Medium, color = textColor)
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

