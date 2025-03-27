package com.a702.finafan.presentation.savings

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.ImageField
import com.a702.finafan.common.ui.component.NumberField
import com.a702.finafan.common.ui.component.StringField

@Composable
fun SavingDepositScreen(
    onComplete: () -> Unit
) {
    val message = remember { mutableStateOf("") }
    val money = remember { mutableStateOf("") }
    val image = remember { mutableStateOf(Uri.EMPTY) }

    SavingScreenLayout(
        title = stringResource(R.string.saving_item_deposit_title),
        buttonText = stringResource(R.string.btn_deposit),
        isButtonEnabled = message.value.isNotEmpty() && money.value.isNotEmpty() && image.value != Uri.EMPTY,
        onButtonClick = {
            /* TODO: 입금 처리 API 호출 */

            onComplete()

            // 입금 확인 다이얼로그
//            ConfirmDialog(content = stringResource(R.string.saving_item_deposit_complete)) {
//                onComplete()
//            }
        }
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

        // 사진 필드
        ImageField(
            modifier = Modifier.padding(top = 34.dp),
            label = stringResource(R.string.saving_item_deposit_image_label),
            selectImage = image
        )

    }
}

@Preview
@Composable
fun SavingDepositPreview() {
    SavingDepositScreen(onComplete = {})
}