package com.a702.finafan.presentation.savings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.StringField
import com.a702.finafan.common.ui.theme.MainTextGray

// 적금 이름 입력 화면
@Composable
fun SavingNameInputScreen(selectName: String) {
    val name = remember { mutableStateOf("") }

    SavingScreenLayout(
        topBarTitle = stringResource(R.string.saving_item_create_top_bar),
        title = stringResource(R.string.saving_item_input_name_title),
        buttonText = stringResource(R.string.btn_next),
        isButtonEnabled = name.value.isNotEmpty(),
        onButtonClick = { /* TODO: 이름 선택 후 다음으로 넘어가기 */ }
    ) {

        // 적금 이름 필드 (선택한 스타 이름이 자동으로 들어감)
        StringField(modifier = Modifier.padding(top = 40.dp, bottom = 12.dp),
            label = stringResource(R.string.saving_item_name_label),
            hint = selectName,
            text = name,
            isSaving = true)

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.info_circle),
                contentDescription = "info",
            )

            Text(
                text = stringResource(R.string.saving_item_input_name_guild),
                color = MainTextGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 24.sp,
                modifier = Modifier.padding(start = 8.dp),
            )
        }

    }

}

@Preview
@Composable
fun SavingNamePreview() {
    SavingNameInputScreen("이찬원")
}