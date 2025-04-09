package com.a702.finafan.presentation.funding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.Pretendard
import com.a702.finafan.common.ui.theme.Typography
import com.a702.finafan.common.utils.StringUtil
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.FundingStatus
import com.a702.finafan.domain.funding.model.Star
import java.time.LocalDate
import java.time.temporal.ChronoUnit

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
            .height(463.dp)
            .background(brush = Brush.linearGradient(
                colors = listOf(colorSet[0].copy(alpha = 0.1f), colorSet[2].copy(alpha = 0.1f))
            ))
            .padding(top = 15.dp, start = 25.dp, end = 25.dp)

    ) {
        Text(text = FundingStatus.valueOf(funding.status).toString(), style = Typography.headlineSmall,
            color = colorSet[2])
        ScreenTitle(funding.title, modifier = Modifier.padding(vertical = 8.dp))
        Text(funding.star.name, modifier = Modifier.padding(bottom = 5.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = Pretendard
        )
        FundingProgressPercentage(funding.currentAmount, funding.goalAmount,
            colorSet[2],
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(13.dp))
        FundingProgressBar(funding.currentAmount, funding.goalAmount,
            listOf(colorSet[0], colorSet[2]),
            modifier = Modifier
        )

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 25.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                modifier = Modifier.align(Alignment.Bottom)
                    .padding(end = 30.dp)
            ) {
                Text(text = "현재 모금액",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Pretendard,
                    color = MainBlack
                    )
                Text(text = StringUtil.formatCurrency(funding.currentAmount),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Pretendard,
                    color = MainBlack
                    )
            }
            Column(
                modifier = Modifier.align(Alignment.Bottom)
                    .padding(start = 20.dp)
            ) {
                Text(text = "목표 금액",
                    style = Typography.displaySmall,
                    color = MainTextGray
                )
                Text(text = StringUtil.formatCurrency(funding.goalAmount),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Pretendard,
                    color = MainTextGray
                )
            }
        }
        Row(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painter,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(0.6f)
                    .clipToBounds()
                    .align(Alignment.Bottom)
            )

            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .align(Alignment.Bottom)
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.End
            ) {
                if (funding.currentAmount >= funding.goalAmount) {
                    SuccessBadge(size = 120.dp, color = colorSet[2])
                    Spacer(modifier = Modifier.height(15.dp))
                }

                Text(
                    "${ChronoUnit.DAYS.between(LocalDate.now(), funding.fundingExpiryDate)}일 뒤 종료",
                    textAlign = TextAlign.End,
                    style = Typography.displaySmall,
                    color = colorSet[2]
                )
            }
        }

    }
}