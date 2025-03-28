package com.a702.finafan.presentation.account

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.StringField

// 계좌 번호 입력 화면
@Composable
fun AccountInputScreen(selectBank: String) {

    val accountNum = remember { mutableStateOf("") }

    ConnectAccountLayout (
        title = stringResource(R.string.connect_account_input_number_title, selectBank),
        buttonText = stringResource(R.string.btn_next),
        isButtonEnabled = accountNum.value.isNotEmpty(),
        onBackClick = { /* TODO: 뒤로 가기 */ },
        onButtonClick = { /* TODO: 다음으로 넘어가기 */ }
    ) {

        // 계좌번호 입력 필드
        StringField(
            modifier = Modifier.padding(top = 34.dp),
            label = stringResource(R.string.account_label),
            hint = stringResource(R.string.account_hint),
            text = accountNum)

    }
}

@Preview
@Composable
fun AccountInputPreview() {
    AccountInputScreen("NH농협")
}