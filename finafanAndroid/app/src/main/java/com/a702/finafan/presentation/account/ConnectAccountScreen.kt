package com.a702.finafan.presentation.account

import androidx.compose.foundation.layout.Column
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

// 인증 코드 확인 완료 화면
@Composable
fun ConnectAccountScreen(
    viewModel: SavingViewModel = viewModel()
) {

    val savingState by viewModel.savingState.collectAsState()
    val account = savingState.connectAccount

    ConnectAccountLayout (
        topBarTitle = stringResource(R.string.saving_account_connect_bank_title),
        title = stringResource(R.string.connect_account_confirm_title),
        buttonText = stringResource(R.string.btn_delete),
        isButtonEnabled = true,
        onButtonClick = { /* TODO: 연결 계좌 삭제 다이얼로그 띄우기 */ }
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
fun ConnectAccountPreview() {
    ConnectAccountScreen()
}