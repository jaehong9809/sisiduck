package com.a702.finafan.presentation.account

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a702.finafan.R
import com.a702.finafan.common.ui.component.NumberField
import com.a702.finafan.presentation.account.viewmodel.AccountViewModel

// 계좌 번호 입력 화면
@Composable
fun AccountInputScreen(
    viewModel: AccountViewModel = viewModel(),
    onComplete: () -> Unit
) {

    val accountState by viewModel.accountState.collectAsState()

    val selectBank = accountState.selectBank
    val accountNo = remember { mutableStateOf("") }

    ConnectAccountLayout (
        title = stringResource(R.string.connect_account_input_number_title, selectBank.bankName),
        buttonText = stringResource(R.string.btn_next),
        isButtonEnabled = accountNo.value.isNotEmpty(),
        onButtonClick = {
            viewModel.updateInputAccountNo(accountNo.value)
            onComplete()
        }
    ) {

        // 계좌번호 입력 필드
        NumberField(
            modifier = Modifier.padding(top = 34.dp),
            label = stringResource(R.string.account_label),
            hint = stringResource(R.string.account_hint),
            text = accountNo
        )

    }
}

@Preview
@Composable
fun AccountInputPreview() {
    AccountInputScreen(onComplete = {})
}