package com.a702.finafan.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.a702.finafan.common.ui.theme.Typography

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    height: Dp = 32.dp,
    widthMin: Dp = Dp.Unspecified,
    widthMax: Dp = Dp.Unspecified,
    content: String,
    fontColor: Color,
    bgColor: Color,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    Box(
        modifier = modifier
            .height(height)
            .widthIn(min = widthMin, max = widthMax)
            .background(bgColor, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = content, style = Typography.titleMedium, color = fontColor, fontSize = fontSize)
    }
}
