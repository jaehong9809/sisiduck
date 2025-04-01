package com.a702.finafan.presentation.account

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.MainTextGray

// 전체 계좌 목록 화면
@Composable
fun AllAccountScreen() {
    Text(text = stringResource(R.string.saving_item_empty_star),
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        color = MainTextGray,
        textAlign = TextAlign.Center,
        lineHeight = 24.sp
    )
}

@Preview
@Composable
fun AllAccountPreview() {
    AllAccountScreen()
}