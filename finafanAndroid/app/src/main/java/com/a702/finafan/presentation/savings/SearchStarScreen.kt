package com.a702.finafan.presentation.savings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.common.ui.component.CommonCloseTopBar
import com.a702.finafan.common.ui.component.PrimaryGradBottomButton
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite

@Composable
fun SearchStarScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        // 상단 바
        CommonCloseTopBar(modifier = Modifier, imageOnClick = {}, text = "스타 목록")

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .background(MainWhite)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "스타 검색 페이지",
                color = MainBlack,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 36.dp),
                textAlign = TextAlign.Start
            )

            // TODO: 스타 검색 필드 추가

            // TODO: 스타이름 아이템 LazyColumn 추가
        }

        // 하단 버튼
        PrimaryGradBottomButton(modifier = Modifier, onClick = {}, text = "다음")
    }
}

@Preview
@Composable
fun SearchStarPreview() {
    SearchStarScreen()
}