package com.a702.finafan.presentation.savings

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.NumberField
import com.a702.finafan.common.ui.component.StringField

@Composable
fun SavingDepositScreen() {
    val message = remember { mutableStateOf("") }
    val money = remember { mutableStateOf("") }

    SavingScreenLayout(
        title = stringResource(R.string.saving_item_deposit_title),
        buttonText = stringResource(R.string.btn_deposit),
        isButtonEnabled = message.value.isNotEmpty() && money.value.isNotEmpty(),
        onBackClick = { /* TODO: 뒤로 가기 */ },
        onButtonClick = { /* TODO: 입금 처리 */ }
    ) {

        // 메시지 필드
        StringField(
            modifier = Modifier.padding(top = 40.dp, bottom = 12.dp),
            label = stringResource(R.string.saving_message_label),
            hint = stringResource(R.string.saving_message_hint),
            text = message,
            maxLength = 20
        )

        // 입금 금액
        NumberField(
            modifier = Modifier.padding(top = 34.dp, bottom = 12.dp),
            label = stringResource(R.string.money_label),
            hint = stringResource(R.string.money_hint),
            text = money,
            isMoney = true
        )

        // TODO: 사진 추가 필드
    }
}

@Preview
@Composable
fun SavingDepositPreview() {
    SavingDepositScreen()
}