package com.a702.finafan.presentation.savings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.CommonBackTopBar
import com.a702.finafan.common.ui.component.PrimaryGradBottomButton
import com.a702.finafan.common.ui.component.StringField
import com.a702.finafan.common.ui.theme.EditTextGray
import com.a702.finafan.common.ui.theme.MainBlack
import com.a702.finafan.common.ui.theme.MainWhite

@Composable
fun SelectStarScreen() {

    val name = remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .imePadding()
        .windowInsetsPadding(WindowInsets.ime)
    ) {
        // 상단 바
        CommonBackTopBar(modifier = Modifier, imageOnClick = {},
            text = stringResource(R.string.saving_item_create_top_bar), isTextCentered = true)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .background(MainWhite)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.saving_item_select_star_title),
                color = MainBlack,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 36.sp,
                modifier = Modifier.padding(top = 36.dp),
                textAlign = TextAlign.Start
            )

            // 스타이름 필드
            StringField(modifier = Modifier.padding(top = 40.dp, bottom = 12.dp),
                label = stringResource(R.string.star_name_label), hint = stringResource(R.string.name_hint), text = name)

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .height(38.dp)
                        .clickable {
                            // TODO: Navigation 추가 필요, 검색 화면으로 이동
                        }
                        .border(1.dp, EditTextGray, shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.search_btn_label),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = EditTextGray
                    )
                }
            }

        }

        // 하단 버튼
        PrimaryGradBottomButton(
            modifier = Modifier.fillMaxWidth()
                .imePadding(),
            onClick = {},
            text = stringResource(R.string.btn_next),
            isEnabled = name.value.isNotEmpty()
        )
    }
}

@Preview
@Composable
fun SelectStarPreview() {
    SelectStarScreen()
}