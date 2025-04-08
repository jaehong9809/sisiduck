package com.a702.finafan.presentation.funding.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.common.ui.theme.Pretendard
import com.a702.finafan.common.ui.theme.Typography

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
    modifier: Modifier = Modifier
) {
    Text(text = content,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = Pretendard,
        modifier = modifier
    )
}

@Composable
fun MenuDescription(
    content: String,
    modifier: Modifier = Modifier
) {
    // 16.sp, 5A5A5A, Regular
    Text(text = content,
        style = Typography.bodyMedium,
        color = MainTextGray
    )
}