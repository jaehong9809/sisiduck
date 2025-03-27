package com.a702.finafan.presentation.funding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.MainBgGray
import com.a702.finafan.common.ui.theme.Pretendard
import com.a702.finafan.common.utils.StringUtil

@Composable
fun FundingProgressBar(
    currentAmount: Long,
    goalAmount: Long,
    gradientColors: List<Color>,
    modifier: Modifier
) {
    val progress = if (goalAmount > 0) currentAmount.toFloat() / goalAmount.toFloat() else 0f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(MainBgGray, shape = RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .background(
                    brush = Brush.horizontalGradient(gradientColors),
                    shape = RoundedCornerShape(10.dp)
                )
        )
    }


}

@Composable
fun FundingProgressPercentage(
    currentAmount: Long,
    goalAmount: Long,
    color: Color,
    modifier: Modifier
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(text = "${StringUtil.formatPercentage(currentAmount, goalAmount)}% 달성",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            fontFamily = Pretendard,
            color = color,
            textAlign = TextAlign.End,
            modifier = modifier
                .align(Alignment.CenterEnd)
                .padding(top = 14.dp)
        )
    }
}