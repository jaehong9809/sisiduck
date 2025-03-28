package com.a702.finafan.presentation.account

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

// 1원 송금 확인 화면
@Composable
fun AccountSendScreen(
    account: Account,
    onComplete: (Account) -> Unit
) {

    ConnectAccountLayout (
        title = stringResource(R.string.connect_account_send_money_title, account.bank.bankName),
        buttonText = stringResource(R.string.btn_next),
        isButtonEnabled = true,
        onButtonClick = {
            onComplete(account)
        }
    ) {
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

@Preview
@Composable
fun AccountSendPreview() {
    AccountSendScreen(Account(), onComplete = {})
}