package com.a702.finafan.presentation.funding.component

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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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
import com.a702.finafan.domain.funding.model.Funding
import com.a702.finafan.domain.funding.model.Star
import com.a702.finafan.presentation.navigation.NavRoutes
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Composable
fun FundingCardItem(
    star: Star,
    funding: Funding,
    navController: NavHostController
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(star.thumbnail)
            .build()
    )

    Column(
        modifier = Modifier
            .height(260.dp)
            .shadow(10.dp, spotColor = MainBlack.copy(alpha = 0.05f), shape = RoundedCornerShape(20.dp))
            .background(MainWhite)
            .padding(20.dp)
            .clickable {
                navController.navigate(NavRoutes.FundingDetail.withId(funding.id))
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
                                    colors = listOf(starThemes[star.index].start, starThemes[star.index].end)
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
                    Badge(content = star.name, fontColor =  MainWhite, bgColor =  starThemes[star.index].mid)
                }
                Text(
                    "종료까지 ${ChronoUnit.DAYS.between(LocalDate.now(), funding.fundingExpiryDate)}일",
                    style = titleMedium,
                    color = MainTextGray
                )
            }

            Text(text = funding.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Pretendard,
                modifier = Modifier.padding(horizontal = 0.dp, vertical = 20.dp))
        Row(modifier = Modifier.fillMaxWidth()
            .padding(bottom = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End) {
            Text(text = "목표금액  ", color = MainTextGray, fontFamily = Pretendard, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(text = StringUtil.formatCurrency(funding.goalAmount), color = MainTextGray, fontFamily = Pretendard, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }
            FundingProgressBar(funding.currentAmount, funding.goalAmount, listOf(starThemes[star.index].start, starThemes[star.index].end), modifier = Modifier)
            FundingProgressPercentage(funding.currentAmount, funding.goalAmount,
                starThemes[star.index].end,
                modifier = Modifier
            )
    }
}