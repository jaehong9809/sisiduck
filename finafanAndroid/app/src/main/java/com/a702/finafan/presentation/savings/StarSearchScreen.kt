package com.a702.finafan.presentation.savings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.AddStarDialog
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.PrimaryGradBottomButton
import com.a702.finafan.common.ui.component.SearchField
import com.a702.finafan.common.ui.theme.MainBgLightGray
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.domain.savings.model.Star
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

// 스타 선택 화면
@Composable
fun StarSearchScreen(
    onSelect: () -> Unit,
    viewModel: SavingViewModel = viewModel()
) {

    val selectStar = remember { mutableStateOf(Star()) }
    val showDialog = rememberSaveable { mutableStateOf(false) }
    val starState by viewModel.starState.collectAsState()

    if (showDialog.value) {
        // 스타 추가 확인 다이얼로그
        AddStarDialog(
            showDialog,
            selectStar.value,
            onClickConfirm = {
                showDialog.value = false

                viewModel.updateSavingStar(selectStar.value)
                onSelect()
            }
        )
    }

    LaunchedEffect(Unit) {
        viewModel.fetchStars()
    }

    Scaffold(
        topBar = {
            CommonBackTopBar(
                modifier = Modifier,
                text = stringResource(R.string.saving_item_star_select_title),
                isTextCentered = true
            )
        },
        bottomBar = {
            PrimaryGradBottomButton(
                modifier = Modifier,
                onClick = {
                    showDialog.value = true
                },
                text = stringResource(R.string.btn_select),
                isEnabled = selectStar.value.starId > 0
            )
        }
    ) { innerPadding ->
        Column(modifier =
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .imePadding()
                .windowInsetsPadding(WindowInsets.ime)
        ) {
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
                        // 스타 검색
                        viewModel.fetchStars(name.value)
                    }
                )

                // 스타 목록
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (starState.stars.isEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.saving_item_empty_star),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = MainTextGray,
                                textAlign = TextAlign.Center,
                                lineHeight = 24.sp
                            )
                        }
                    } else {
                        items(starState.stars) { starItem ->
                            Column(
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) {
                                StarItem(starItem,
                                    isSelected = starItem.starId == selectStar.value.starId,
                                    onSelect = { select ->
                                        selectStar.value = select
                                    })
                            }
                        }
                    }
                }
            }
        }
    }

}


@Preview
@Composable
fun SearchStarPreview() {
    StarSearchScreen(onSelect = {})
}