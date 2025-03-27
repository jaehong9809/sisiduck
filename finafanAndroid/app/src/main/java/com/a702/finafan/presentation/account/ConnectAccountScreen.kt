package com.a702.finafan.presentation.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.presentation.savings.AccountInfoItem

// 인증 코드 확인 완료 화면
@Composable
fun ConnectAccountScreen(selectBank: String) {

    ConnectAccountLayout (
        topBarTitle = stringResource(R.string.saving_account_connect_bank_title),
        title = stringResource(R.string.connect_account_verification_code_confirm_title, selectBank),
        buttonText = stringResource(R.string.btn_delete),
        isButtonEnabled = true,
        onBackClick = { /* TODO: 뒤로 가기 */ },
        onButtonClick = { /* TODO: 연결 계좌 삭제 다이얼로그 띄우기 */ }
    ) {

        Column {
            AccountInfoItem(
                modifier = Modifier.padding(top = 34.dp),
                account = Account(
                    accountId = 1234,
                    accountNo = "456-789-1000",
                    bank = Bank(bankId = 12, bankCode = "345", bankName = "NH농협")
                ),
                fontColor = MainTextGray
            )
        }

    }
}

@Preview
@Composable
fun ConnectAccountPreview() {
    ConnectAccountScreen("NH농협")
}