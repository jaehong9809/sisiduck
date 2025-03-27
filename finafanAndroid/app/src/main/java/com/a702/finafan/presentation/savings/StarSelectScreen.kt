package com.a702.finafan.presentation.savings

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.StringField
import com.a702.finafan.common.ui.component.SubButton

// 적금 가입할 스타 선택 화면
@Composable
fun StarSelectScreen(

) {

    val name = remember { mutableStateOf("") }

    SavingScreenLayout(
        topBarTitle = stringResource(R.string.saving_item_create_top_bar),
        title = stringResource(R.string.saving_item_select_star_title),
        buttonText = stringResource(R.string.btn_next),
        isButtonEnabled = name.value.isNotEmpty(),
        onButtonClick = {
            /* TODO: 스타 선택 후 다음으로 넘어가기 */
            // TODO: 검색화면에서 받아온거면 바로 넘어가기
            //  직접 입력한 경우에는 스타가 존재하는지 확인 필요, 연예인코드 같은 거 받아와서 객체로 만들기
        }
    ) {

        // 스타 이름 필드
        StringField(
            modifier = Modifier.padding(top = 40.dp, bottom = 12.dp),
            label = stringResource(R.string.star_name_label), hint = stringResource(R.string.name_hint), text = name)

        SubButton(
            text = stringResource(R.string.search_btn_label),
            fontSize = 16.sp,
            onButtonClick = { /* TODO: 검색 화면으로 이동 */ })
    }

}

@Preview
@Composable
fun SelectStarPreview() {
    StarSelectScreen()
}