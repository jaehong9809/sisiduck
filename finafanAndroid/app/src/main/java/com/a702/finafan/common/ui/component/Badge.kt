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
import androidx.compose.ui.unit.dp
import com.a702.finafan.common.ui.theme.Typography

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    content: String,
    fontColor: Color,
    bgColor: Color,
) {
    Box(
        modifier = modifier
            .height(32.dp)
            .widthIn()
            .background(bgColor, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = content, style = Typography.titleMedium, color = fontColor)
    }

}