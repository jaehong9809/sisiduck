package com.a702.finafan.presentation.account

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.theme.MainTextGray
import com.a702.finafan.domain.savings.model.Account
import com.a702.finafan.domain.savings.model.Bank
import com.a702.finafan.presentation.savings.AccountInfoItem
import com.a702.finafan.presentation.savings.viewmodel.SavingViewModel

// 1원 송금 확인 화면
@Composable
fun AccountSendScreen(
    viewModel: SavingViewModel = viewModel(),
    onComplete: () -> Unit
) {

    val savingState by viewModel.savingState.collectAsState()
    val selectBank = savingState.selectBank

    ConnectAccountLayout (
        title = stringResource(R.string.connect_account_send_money_title, selectBank.bankName),
        buttonText = stringResource(R.string.btn_next),
        isButtonEnabled = true,
        onButtonClick = {
            onComplete()
        }
    ) {
        AccountInfoItem(
            modifier = Modifier.padding(top = 34.dp),
            account = Account(
                accountNo = savingState.inputAccountNo,
                bank = Bank(
                    bankId = selectBank.bankId,
                    bankCode = selectBank.bankCode,
                    bankName = selectBank.bankName
                )
            ),
            fontColor = MainTextGray
        )
    }
}

@Preview
@Composable
fun AccountSendPreview() {
    AccountSendScreen(onComplete = {})
}