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
fun AccountCodeConfirmScreen(account: Account,) {

    ConnectAccountLayout (
        title = stringResource(R.string.connect_account_verification_code_confirm_title, account.bank.bankName),
        buttonText = stringResource(R.string.btn_confirm),
        isButtonEnabled = true,
        onButtonClick = { /* TODO: 연결 계좌 목록 화면으로 넘어가기 */ }
    ) {

        Column {
            AccountInfoItem(
                modifier = Modifier.padding(top = 34.dp),
                account = Account(
                    accountNo = account.accountNo,
                    bank = Bank(
                        bankId = account.bank.bankId,
                        bankCode = account.bank.bankCode,
                        bankName = account.bank.bankName
                    )
                ),
                fontColor = MainTextGray
            )
        }

    }
}

@Preview
@Composable
fun AccountCodeConfirmPreview() {
    AccountCodeConfirmScreen(Account())
}