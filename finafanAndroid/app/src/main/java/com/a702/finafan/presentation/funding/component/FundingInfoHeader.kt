package com.a702.finafan.presentation.funding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.Badge
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.MainWhite
import com.a702.finafan.common.ui.theme.starThemes
import com.a702.finafan.common.utils.StringUtil.getFundingProgressText
import com.a702.finafan.common.utils.StringUtil.getRemainingAmountText
import com.a702.finafan.common.utils.StringUtil.getRemainingDaysText
import com.a702.finafan.domain.funding.model.Funding

@Composable
fun FundingInfoHeader(
    funding: Funding
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(funding.star.thumbnail)
            .build()
    )

    Column(
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(starThemes[funding.star.index].start, starThemes[funding.star.index].end)
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
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Badge(funding.star.name, MainWhite, starThemes[funding.star.index].mid)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column() {
                        Text(text = getRemainingDaysText(funding.fundingExpiryDate),
                            fontSize = 12.sp,
                            color = MainTextGray)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = getFundingProgressText(funding.currentAmount, funding.goalAmount),
                            fontSize = 12.sp,
                            color = starThemes[funding.star.index].end)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = funding.title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = getRemainingAmountText(funding.currentAmount, funding.goalAmount),
            fontSize = 16.sp,
            color = starThemes[funding.star.index].mid,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(starThemes[funding.star.index].start, starThemes[funding.star.index].end)
                    )
                )
                .clickable(onClick = { /* 모금 상세 */ })
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text(
                text = stringResource(R.string.funding_detail),
                color = MainWhite,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

}