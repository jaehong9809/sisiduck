package com.a702.finafan.presentation.funding

import android.graphics.drawable.shapes.Shape
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.common.ui.component.Badge
import com.a702.finafan.common.ui.theme.CustomTypography.titleMedium
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.Pretendard
import com.a702.finafan.common.ui.theme.starThemes
import com.a702.finafan.common.utils.StringUtil
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.concurrent.TimeUnit

@Composable
fun FundingCardItem(
    star: Star,
    funding: Funding,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(star.image)
            .build()
    )

    Column(
        modifier = Modifier
            .height(250.dp)
            .shadow(10.dp, spotColor = MainBlack.copy(alpha = 0.05f), shape = RoundedCornerShape(20.dp)) // 외부 그림자 추가
            .background(MainWhite)
            .padding(20.dp)
            .clickable {
            }
    ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(starThemes[star.index].start, starThemes[star.index].end) // 원하는 그라데이션 색상 설정
                                )
                            )
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = "Star Image",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(color = MainWhite)
                                .align(Alignment.Center)
                                .border(2.dp, color = MainWhite, shape = CircleShape)
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Badge(star.name, MainWhite, starThemes[star.index].mid)
                }
                Text(
                    "종료까지 ${ChronoUnit.DAYS.between(LocalDate.now(), funding.fundingEndDate)}일",
                    style = titleMedium,
                    color = MainTextGray
                )
            }

            Text(text = funding.fundingTitle,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Pretendard,
                modifier = Modifier.padding(horizontal = 0.dp, vertical = 20.dp))
        Row(modifier = Modifier.fillMaxWidth()
            .padding(bottom = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End) {
            Text(text = "목표금액  ", color = MainTextGray, fontFamily = Pretendard, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(text = StringUtil.formatCurrency(funding.fundingGoalAmount), color = MainTextGray, fontFamily = Pretendard, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }
            FundingProgressBar(funding.fundingCurrentAmount, funding.fundingGoalAmount, listOf(starThemes[star.index].start, starThemes[star.index].end))
    }
}