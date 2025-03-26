package com.a702.finafan.presentation.funding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.common.ui.theme.Pretendard
import com.a702.finafan.common.utils.StringUtil

@Composable
fun FundingDetailHeader(
    funding: Funding,
    star: Star,
    colorSet: List<Color>,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(star.image)
            .build()
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(515.dp)
            .background(brush = Brush.linearGradient(
                colors = listOf(colorSet[0].copy(alpha = 0.1f), colorSet[2].copy(alpha = 0.1f))
            ))
            .padding(top = 10.dp, start = 25.dp, end = 25.dp)

    ) {
        ScreenTitle(funding.fundingTitle, modifier = Modifier.padding(vertical = 8.dp))
        Text(funding.starName, modifier = Modifier.padding(bottom = 10.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = Pretendard
        )
        FundingProgressPercentage(funding.fundingCurrentAmount, funding.fundingGoalAmount,
            colorSet[2],
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(13.dp))
        FundingProgressBar(funding.fundingCurrentAmount, funding.fundingGoalAmount,
            listOf(colorSet[0], colorSet[2]),
            modifier = Modifier
        )

        Row() {
            Column() {
                Text("현재 모금액")
                Text(StringUtil.formatCurrency(funding.fundingCurrentAmount))
            }
            Column() {
                Text("목표 금액")
                Text(StringUtil.formatCurrency(funding.fundingGoalAmount))
            }
        }
        Row ( modifier = Modifier.fillMaxSize() ) {
            Image(
                painter = painter,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.weight(0.8f)
                    .clipToBounds()
                    .align(Alignment.Bottom)
            )
            Text("7일 뒤 종료",
                modifier = Modifier.weight(0.2f)
                    .align(Alignment.Bottom))

        }
    }
}