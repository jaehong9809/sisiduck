package com.a702.finafan.presentation.funding

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.Pretendard

@Composable
fun ScreenTitle(
    content: String,
    modifier: Modifier
) {
    Text(text = content,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = Pretendard,
        modifier = modifier
    )
}

@Composable
fun MenuTitle(
    content: String,
    modifier: Modifier
) {
    Text(text = content,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = Pretendard,
        modifier = modifier
    )
}