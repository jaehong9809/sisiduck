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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.PrimaryGradBottomButton
import com.a702.finafan.common.ui.component.SearchField
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

// 스타 선택 화면
@Composable
fun StarSearchScreen(
    onSelect: (Star) -> Unit,
    viewModel: SavingViewModel = viewModel()
) {

    val selectStar = remember { mutableStateOf(Star()) }

    val context = LocalContext.current
    val uiState by viewModel.starState.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchStars()
    }

    Column(modifier =
        Modifier
            .fillMaxSize()
            .imePadding()
            .windowInsetsPadding(WindowInsets.ime)
    ) {
        // 상단 바
        CommonBackTopBar(
            modifier = Modifier,
            text = stringResource(R.string.saving_item_star_select_title),
            isTextCentered = true
        )

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

            // 스타 목록
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(uiState.stars) { starItem ->
                    Column(
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        StarItem(starItem,
                            isSelected = starItem.entertainerId == selectStar.value.entertainerId,
                            onSelect = { select ->
                                selectStar.value = select
                            })
                    }
                }
            }
        }

        // 하단 버튼
        PrimaryGradBottomButton(
            modifier = Modifier,
            onClick = {
                // TODO: 스타 추가 확인 다이얼로그
                // 여기서 추가 버튼 누르면 적금 이름 페이지로 이동
                onSelect(selectStar.value)
            },
            text = stringResource(R.string.btn_select),
            isEnabled = selectStar.value.entertainerId > 0)
    }
}


@Preview
@Composable
fun SearchStarPreview() {
    StarSearchScreen(onSelect = {})
}