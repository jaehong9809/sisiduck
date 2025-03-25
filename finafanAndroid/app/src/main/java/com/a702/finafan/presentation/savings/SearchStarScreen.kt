package com.a702.finafan.presentation.savings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonCloseTopBar
import com.a702.finafan.common.ui.component.PrimaryGradBottomButton
import com.a702.finafan.common.ui.component.SearchField
import com.a702.finafan.common.ui.theme.MainBgLightGray

@Composable
fun SearchStarScreen() {
    val starList = mutableListOf(
        Star("", "이찬원"), Star("", "임영웅"), Star("", "권민채")
    )

    Column(modifier =
        Modifier
            .fillMaxSize()
            .imePadding()
            .windowInsetsPadding(WindowInsets.ime)
    ) {
        // 상단 바
        CommonCloseTopBar(modifier = Modifier, imageOnClick = {}, text = stringResource(R.string.saving_item_star_list_title))

        Column(
            modifier = Modifier
                .background(MainBgLightGray)
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp, top = 22.dp)
        ) {

            // 검색창
            val name = remember { mutableStateOf("") }
            SearchField(
                modifier = Modifier.padding(bottom = 22.dp),
                text = name,
                onClick = {
                    // TODO: 스타 검색 API 호출
                }
            )

            // TODO: 스타 목록 API 호출

            // 스타 목록
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(starList) { star ->
                    Column(
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        StarItem(star)
                    }
                }
            }
        }

        // 하단 버튼
        PrimaryGradBottomButton(modifier = Modifier, onClick = {}, text = stringResource(R.string.btn_select))
    }
}


@Preview
@Composable
fun SearchStarPreview() {
    SearchStarScreen()
}