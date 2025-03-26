package com.a702.finafan.presentation.savings

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.StringField
import com.a702.finafan.common.ui.theme.EditTextGray

// 적금 가입할 스타 선택 화면
@Composable
fun StarSelectScreen() {

    val name = remember { mutableStateOf("") }

    SavingScreenLayout(
        topBarTitle = stringResource(R.string.saving_item_create_top_bar),
        title = stringResource(R.string.saving_item_select_star_title),
        buttonText = stringResource(R.string.btn_next),
        isButtonEnabled = name.value.isNotEmpty(),
        onButtonClick = { /* TODO: 스타 선택 후 다음으로 넘어가기 */ }
    ) {

        // 스타 이름 필드
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

}

@Preview
@Composable
fun SelectStarPreview() {
    StarSelectScreen()
}