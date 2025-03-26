package com.a702.finafan.presentation.account

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.presentation.savings.Account
import com.a702.finafan.presentation.savings.AccountInfoItem

// 1원 송금 확인 화면
@Composable
fun AccountSendScreen(selectBank: String) {

    ConnectAccountLayout (
        title = stringResource(R.string.connect_account_send_money_title, selectBank),
        buttonText = stringResource(R.string.btn_next),
        isButtonEnabled = true,
        onBackClick = { /* TODO: 뒤로 가기 */ },
        onButtonClick = { /* TODO: 다음으로 넘어가기 */ }
    ) {
        AccountInfoItem(
            modifier = Modifier.padding(top = 34.dp),
            account = Account("NH농협", "12-345-678900"),
            fontColor = MainTextGray
        )
    }
}

@Preview
@Composable
fun AccountSendPreview() {
    AccountSendScreen("NH농협")
}