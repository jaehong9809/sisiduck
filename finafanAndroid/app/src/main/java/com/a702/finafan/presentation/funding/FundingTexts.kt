package com.a702.finafan.presentation.funding

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.theme.Pretendard

@Composable
fun ScreenTitle(
    content: String
) {
    Text(text = content,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = Pretendard,
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 30.dp, bottom = 10.dp)
    )
}

@Composable
fun MenuTitle(
    content: String
) {
    Text(text = content,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = Pretendard,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
    )
}