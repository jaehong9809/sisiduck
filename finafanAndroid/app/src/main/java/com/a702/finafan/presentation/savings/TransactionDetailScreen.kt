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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.theme.MainBlackWithTransparency
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.Shadow.innerShadow
import com.a702.finafan.common.ui.theme.gradientList
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel
import kotlin.random.Random

// 적금 거래 내역 상세 화면
@Composable
fun TransactionDetailScreen(
    viewModel: SavingViewModel = viewModel(),
    onNavigateClick: () -> Unit
) {

    val savingState by viewModel.savingState.collectAsState()
    val transaction = savingState.transaction

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(transaction.imageUrl)
            .build()
    )

    val randomGradient = gradientList[Random.nextInt(gradientList.size)]

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CommonBackTopBar(
            textOnClick = {
                onNavigateClick()
            },
            text = stringResource(R.string.saving_history_title))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .innerShadow(
                    shape = RectangleShape,
                    color = MainBlackWithTransparency,
                    blur = 100.dp,
                    offsetX = 0.dp,
                    offsetY = (-20).dp,
                    spread = 0.dp
                ),
        ) {

            Image(
                painter = painter,
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxSize()
                    .background(randomGradient)
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )

            // TODO: 이미지가 없으면 디폴트 gradient 띄우기

            Column(
                modifier = Modifier
                    .padding(bottom = 48.dp, start = 24.dp, end = 24.dp)
                    .align(Alignment.BottomStart),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = transaction.date,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MainWhite, lineHeight = 24.sp,
                )

                Text(
                    text = transaction.message,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainWhite,
                    lineHeight = 24.sp,
                )

                Text(
                    text = StringUtil.formatCurrency(transaction.amount),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainWhite,
                    lineHeight = 24.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTransactionDetail() {
    TransactionDetailScreen(
        onNavigateClick = {}
    )
}